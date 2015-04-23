package com.counter.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.counter.exception.EditStaticException;
import com.counter.exception.EmptyStatisticException;
import com.counter.service.StatisticService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.portal.domen.ServiceType;
import com.portal.domen.Statistic;
import com.portal.utils.Helper;
import com.portal.utils.SqlUtil;

@Controller
@RequestMapping("VIEW")
public class CounterPortletController {

	private static final String STATISTICS = "statistics";
	private static final String VIEW = "view";
	private static final String SERVICE = "service";
	private static final String TOTAL_PRICE = "totalPrice";
	private static final String SEARCH_YEAR = "searchYear";
	private static final String SEARCH_MONTH = "searchMonth";
	private static final String EMPTY_ERROR = "emptyError";
	private static final String EDIT_ERROR = "editError";
	private static final String SUCCESS = "success";
	
	private static Logger LOGGER = Logger.getLogger(CounterPortletController.class);

	private List<Statistic> statistics;

	@Autowired
	private StatisticService statisticService;

	@RenderMapping
	public String handleViewRequest(RenderRequest renderRequest, RenderResponse renderResponse) {
		int month = ParamUtil.get(renderRequest, SEARCH_MONTH, 0);
		int year = ParamUtil.get(renderRequest, SEARCH_YEAR, 0);

		statistics = statisticService.getAllStatisticByPeriod(getUser(renderRequest).getUserId(), month, year);

		renderRequest.setAttribute(STATISTICS, statistics);
		renderRequest.setAttribute(TOTAL_PRICE, Helper.getTotalPrice(statistics));

		return VIEW;
	}

	@ActionMapping(params = "action=addService")
	public void addService(ActionRequest actionRequest, ActionResponse actionResponse, Model model) throws IOException, PortletException {
		actionResponse.setRenderParameter(SEARCH_MONTH, ParamUtil.get(actionRequest, SEARCH_MONTH, "0"));
		actionResponse.setRenderParameter(SEARCH_YEAR, ParamUtil.get(actionRequest, SEARCH_YEAR, "0"));

		Statistic statistic = new Statistic();
		statistic.setService(ServiceType.valueOf(ParamUtil.get(actionRequest, SERVICE, 0)));
		statistic.setMonth(ParamUtil.get(actionRequest, SqlUtil.FIELD_MONTH, 0));
		statistic.setYear(ParamUtil.get(actionRequest, SqlUtil.FIELD_YEAR, 0));
		statistic.setValue(ParamUtil.get(actionRequest, SqlUtil.FIELD_VALUE, 0));
		statistic.setUserId(getUser(actionRequest).getUserId());

		try {
			statisticService.addStatistic(statistic);
			SessionMessages.add(actionRequest, SUCCESS);
			LOGGER.info("Statistic successfully added");
		} catch (EmptyStatisticException e) {
			LOGGER.error(e.getMessage(), e);
			SessionErrors.add(actionRequest, EMPTY_ERROR);
		} catch (EditStaticException e) {
			SessionErrors.add(actionRequest, EDIT_ERROR);
			LOGGER.error(e.getMessage(), e);
		}
	}

	@ResourceMapping
	public void generatePDF(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws PortletException, IOException {

		String totalPrice = ParamUtil.getString(resourceRequest, TOTAL_PRICE);

		try {
			Document document = new Document();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(document, baos);

			document.open();

			addTitlePage(document);
			createTable(document, resourceRequest);
			addTotalPrice(document, resourceRequest, totalPrice);

			document.close();

			resourceResponse.setContentType("application/pdf");
			resourceResponse.addProperty(HttpHeaders.CACHE_CONTROL, "max-age=3600, must-revalidate");
			resourceResponse.setContentLength(baos.size());

			OutputStream out = resourceResponse.getPortletOutputStream();
			baos.writeTo(out);

			out.flush();
			out.close();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	private static User getUser(PortletRequest request) {
		return (User) request.getAttribute(WebKeys.USER);
	}

	private void addTitlePage(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph("Counter statistic", new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));
		document.add(preface);
	}

	private void addTotalPrice(Document document, ResourceRequest resourceRequest, String totalPrice) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		paragraph.add(new Paragraph(" "));
		paragraph.setAlignment(Element.ALIGN_RIGHT);

		paragraph.add(new Paragraph("Total price: " + totalPrice.toString()));
		document.add(paragraph);
	}

	private void createTable(Document document, ResourceRequest resourceRequest) throws DocumentException {

		Paragraph tableParagraph = new Paragraph();
		PdfPTable table = new PdfPTable(4);

		PdfPCell cell = new PdfPCell(new Phrase("Service"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Period"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Value of counter"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Price (RUB)"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		for (Statistic statistic : statistics) {
			cell = new PdfPCell(new Phrase(statistic.getService().getDescription()));
			cell.setPadding(3f);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(statistic.getMonth() + "/" + statistic.getYear()));
			cell.setPadding(3f);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(String.valueOf(statistic.getValue())));
			cell.setPadding(3f);
			table.addCell(cell);
			cell = new PdfPCell(new Phrase(statistic.getPrice().toString()));
			cell.setPadding(3f);
			table.addCell(cell);
		}

		tableParagraph.add(table);
		document.add(tableParagraph);

	}
}
