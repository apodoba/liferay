<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="java.util.Map"%>

<portlet:defineObjects />

<%
	Map<String, String> exchangeRates = (Map<String, String>) renderRequest.getAttribute("exchangeRates");
	List<String> quotes = new ArrayList<String>();
	quotes.addAll(exchangeRates.keySet());
%>

<liferay-ui:error key="error" message="exchange.error" />
<div style="overflow-y: Auto; height: 742px; overflow-x: hidden" >
<liferay-ui:search-container emptyResultsMessage="Empty">
    <liferay-ui:search-container-results results="<%= quotes %>"
        total="<%= quotes.size() %>" 
    />

    <liferay-ui:search-container-row
        className="String"
        modelVar="quote" escapedModel="<%= true %>" >
         
        <liferay-ui:search-container-column-text
            name="exchange.name"
            value="<%=quote%>"/>
            
        <liferay-ui:search-container-column-text
            name="exchange.rates"
            value="<%=exchangeRates.get(quote)%>"/>
       
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator paginate="<%=false%>" />

</liferay-ui:search-container>
</div>