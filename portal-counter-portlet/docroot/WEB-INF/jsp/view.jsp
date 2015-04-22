<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.lang.Integer"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="com.liferay.portal.kernel.util.CalendarFactoryUtil" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Calendar"%>
<%@page import="com.portal.domen.Service"%>
<%@page import="com.portal.utils.Helper"%>
<%@page import="com.portal.utils.Month"%>
<%@page import="com.portal.domen.Statistic"%>


<portlet:defineObjects />

<%
	List<Statistic> statistics = (List<Statistic>) renderRequest.getAttribute("statistics");
	BigDecimal totalPrice = (BigDecimal) renderRequest.getAttribute("totalPrice");
	
	Object monthAttribute = renderRequest.getPortletSession().getAttribute("searchMonth");
	Object yearAttribute = renderRequest.getPortletSession().getAttribute("searchYear");
	
	int searchYear = yearAttribute != null ? (Integer) yearAttribute : 0;
	int searchMonth = monthAttribute != null ? (Integer) monthAttribute : 0;
%>

<portlet:renderURL var="searchServiceURl">
	<portlet:param name="action" value="searchService" />
</portlet:renderURL>
<portlet:actionURL name="addService" var="addServiceURL">
    <portlet:param name="action" value="addService" />
</portlet:actionURL>
<portlet:resourceURL var="reportPDFUrl">
	<portlet:param name="totalPrice" value="<%=totalPrice.toString() %>" />
</portlet:resourceURL>

<aui:form action="<%=searchServiceURl%>" method="post">
	<aui:fieldset>
		<aui:layout>
			<aui:column>
				<aui:select label="counter.month" name="searchMonth">
					<aui:option selected="<%=true%>" value="<%=0%>">Select month</aui:option>
					<%
						for (Month month : Month.values()) {
					%>
					<aui:option selected="<%=searchMonth == month.getId() ? true:false%>" value="<%=month.getId()%>"><%=month.name()%></aui:option>
					<%
						}
					%>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:select label="counter.year" name="searchYear">
					<aui:option selected="<%=true%>" value="<%=0%>">Select year</aui:option>
					<%
						for (Integer year : Helper.getListOfYears(10)) {
					%>
					<aui:option selected="<%=searchYear == year ? true:false%>" value="<%=year%>"><%=year%></aui:option>
					<%
						}
					%>
				</aui:select>
			</aui:column>
			<aui:column columnWidth="30">
				<aui:button-row>
					<aui:button type="submit" value="Search" />
				</aui:button-row>
			</aui:column>
			<aui:column>
				<aui:fieldset label="counter.total.price" />
			</aui:column>
			<aui:column>
					<aui:fieldset label="<%=totalPrice.toString() %>" />
					<input type="button" value="Export to pdf" onClick="location.href = '<%=reportPDFUrl %>'" />
			</aui:column>
		</aui:layout>
	</aui:fieldset>
</aui:form>
<div style="overflow-y: Auto; height: 300px; overflow-x: hidden" >
	<liferay-ui:search-container emptyResultsMessage="Empty">
    <liferay-ui:search-container-results results="<%= statistics %>"
        total="<%= statistics.size() %>" 
    />

    <liferay-ui:search-container-row
        className="Statistic"
        keyProperty="statisticId"
        modelVar="statistic" escapedModel="<%= true %>" >
         
        <liferay-ui:search-container-column-text
            name="counter.service"
            value="<%=statistic.getService().getDescription()%>"/>
            
        <liferay-ui:search-container-column-text
            name="counter.period"
            value="<%=statistic.getMonth()+\"/\"+statistic.getYear()%>"
        />
        
        <liferay-ui:search-container-column-text
            name="counter.value"
            property="value"/>
        
        <liferay-ui:search-container-column-text
            name="counter.price.rub" 
            property="price"
        />  
 
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator paginate="<%=false%>" />

</liferay-ui:search-container>
	
</div>
<br/>
<br/>
<liferay-ui:error key="emptyError" message="counter.empty.error" />
<liferay-ui:error key="editError" message="counter.edit.error" />
<liferay-ui:success key="success" message="counter.success"/>
<aui:form action="<%=addServiceURL%>" method="post">
	<aui:fieldset label="counter.add.statistic">
		<aui:layout>
			<aui:column>
			<aui:input name="searchYear" type="hidden" value="<%=searchYear %>"/>
			<aui:input name="searchMonth" type="hidden" value="<%=searchMonth %>"/>
				<aui:select label="counter.service" name="service">
					<%
						for (Service service : Service.values()) {
					%>
					<aui:option selected="<%=true%>" value="<%=service.getId()%>"><%=service.getDescription()%></aui:option>
					<%
						}
					%>
				</aui:select>
			</aui:column>
			<aui:column>
				<aui:select label="counter.month" name="month">
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
				<aui:select label="counter.year" name="year">
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
				<aui:input type="number" name="value" label="counter.value"/>
			</aui:column>
			<aui:button-row>
				<aui:button type="submit" value="Submit" />
			</aui:button-row>
		</aui:layout>
	</aui:fieldset>
</aui:form>