package com.payment.notification;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.liferay.portal.model.User;
import com.portal.domen.Payment;

@Component
public class NotificationService {
	
	private static final String SEND_FROM = "arinapodoba@gmail.com";

    private JavaMailSender mailSender;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void sendPaymentEmail(final User user, final Payment payment) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                String subject = "Payment for Public Utilities";
                ByteArrayOutputStream outputStream = null;
                
                try {           
                    outputStream = new ByteArrayOutputStream();
                    writePdf(outputStream, payment);
                    byte[] bytes = outputStream.toByteArray();
                     
                    DataSource dataSource = new ByteArrayDataSource(bytes, "application/pdf");
                    MimeBodyPart pdfBodyPart = new MimeBodyPart();
                    pdfBodyPart.setDataHandler(new DataHandler(dataSource));
                    pdfBodyPart.setFileName("payment.pdf");
                                 
                    MimeMultipart mimeMultipart = new MimeMultipart();
                    mimeMultipart.addBodyPart(pdfBodyPart);
                     
                    mimeMessage.setContent(mimeMultipart);
                    mimeMessage.setSubject(subject);
                    MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                    message.setFrom(SEND_FROM);
                    message.setTo(user.getEmailAddress());
                   
                } catch(Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if(null != outputStream) {
                        try { outputStream.close(); outputStream = null; }
                        catch(Exception ex) { }
                    }
                }
            }
        };
        this.mailSender.send(preparator);
    }
    
    public void writePdf(OutputStream outputStream, Payment payment) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, outputStream);
         
        document.open();
         
        document.addTitle("Payment PDF");
        
        Paragraph preface = new Paragraph();
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph("Payment for Public Utilities",new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD)));
		preface.add(new Paragraph(" "));
		preface.add(new Paragraph(" "));
		document.add(preface);
         
        Paragraph tableParagraph = new Paragraph();
		PdfPTable table = new PdfPTable(4);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	    table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
	     
        PdfPCell cell = new PdfPCell(new Phrase("Month"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Year"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Payment (RUB)"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase("Date"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(String.valueOf(payment.getMonth())));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		cell = new PdfPCell(new Phrase(String.valueOf(payment.getYear())));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);
			
		cell = new PdfPCell(new Phrase(String.valueOf(payment.getValue())));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		cell = new PdfPCell(new Phrase(simpleDateFormat.format(new Date())));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setPadding(10f);
		table.addCell(cell);

		tableParagraph.add(table);
		document.add(tableParagraph);
		document.add(new Paragraph(" "));
		document.add(new Paragraph(" "));
         
		String url = "http://t1.gstatic.com/images?q=tbn:ANd9GcRgZUNpz0BSf3yhRaSygGZk8vwPeCpzOQ5E10-J63HeKHIbntET";
        Image image = Image.getInstance(url);
        image.setAlignment(Element.ALIGN_RIGHT);
        image.scaleAbsolute(80, 40);
        document.add(image);
        
        document.close();
    }

}
