<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.lang.Integer"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@page import="com.portal.domen.BalanceHistory"%>

<portlet:defineObjects />

<%
	List<BalanceHistory> histories = (List<BalanceHistory>) renderRequest.getAttribute("history");
	BigDecimal balance = (BigDecimal) renderRequest.getAttribute("balance");
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:SS");
%>

<portlet:actionURL name="addBalance" var="addBalanceURL">
    <portlet:param name="action" value="addBalance" />
</portlet:actionURL>
<aui:fieldset>
		<aui:layout>
			<aui:column>
				<aui:fieldset label="account.total.balance" />
			</aui:column>
			<aui:column>
				<aui:fieldset label="<%=balance.toString() %>" />
			</aui:column>
		</aui:layout>
	</aui:fieldset>
<div style="overflow-y: Auto; height: 200px; overflow-x: hidden" >
	<liferay-ui:search-container emptyResultsMessage="Empty">
    <liferay-ui:search-container-results results="<%= histories %>"
        total="<%= histories.size() %>" 
    />

    <liferay-ui:search-container-row
        className="BalanceHistory"
        keyProperty="historyId"
        modelVar="history" escapedModel="<%= true %>" >
         
        <liferay-ui:search-container-column-text
            name="account.value"
            property="value"/>
            
        <liferay-ui:search-container-column-text
            name="account.date"
            value="<%=DATE_FORMAT.format(history.getDate()) %>"
        />
        
        <liferay-ui:search-container-column-text
            name="account.operation"
            property="operation"/> 
 
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator paginate="<%=false%>" />

</liferay-ui:search-container>
	
</div>
<br/>
<br/>
<liferay-ui:error key="emptyError" message="account.empty.error" />
<liferay-ui:success key="success" message="account.success"/>
<aui:form action="<%=addBalanceURL%>" method="post">
	<aui:fieldset label="account.add.balance">
		<aui:layout>
			<aui:column>
				<aui:input type="number" name="value" label="account.value"/>
			</aui:column>
			<aui:button-row>
				<aui:button type="submit" value="Submit" />
			</aui:button-row>
		</aui:layout>
	</aui:fieldset>
</aui:form>