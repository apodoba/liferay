<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="java.util.List"%>
<%@page import="com.portal.utils.Helper"%>
<%@page import="com.portal.utils.Month"%>

<portlet:defineObjects />

<portlet:actionURL name="addPayment" var="addPaymentURL">
    <portlet:param name="action" value="addPayment" />
</portlet:actionURL>

<liferay-ui:error key="paymentError" message="payment.payment.error" />
<liferay-ui:error key="emptyError" message="payment.empty.error" />
<liferay-ui:error key="balanceError" message="payment.balance.error" />
<liferay-ui:error key="servicePriceError" message="payment.service.price.error"/>
<liferay-ui:success key="success" message="payment.success"/>

<aui:form action="<%=addPaymentURL%>" method="post">
	<aui:fieldset label="payment.add.payment">
		<aui:layout>
			<aui:column>
				<aui:select label="payment.month" name="month">
					<%
						for (Month month : Month.values()) {
					%>
					<aui:option selected="<%=true%>" value="<%=month.getId()%>"><%=month.name()%></aui:option>
					<%
						}
					%>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:select label="payment.year" name="year">
					<%
						for (Integer year : Helper.getListOfYears(10)) {
					%>
					<aui:option selected="<%=year==Helper.getCurrentYear()? true : false%>" value="<%=year%>"><%=year%></aui:option>
					<%
						}
					%>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:input type="number" name="value" label="payment.value"/>
			</aui:column>
			<aui:button-row>
				<aui:button type="submit" value="Submit" />
			</aui:button-row>
		</aui:layout>
	</aui:fieldset>
</aui:form>