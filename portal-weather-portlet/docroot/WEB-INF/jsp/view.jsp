<%@page import="sun.net.www.content.audio.wav"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.weather.util.Weather"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.ArrayList"%>

<portlet:defineObjects />

<%
	Weather currentWeather = (Weather) renderRequest.getAttribute("weather");
	ArrayList<Weather> temp = new ArrayList<Weather>();
	temp.add(currentWeather);

	SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
	String imgName = currentWeather.getHumidity() < 76 ? "sun.gif" : (currentWeather.getHumidity()<90 ? "clouds.gif":"rain.gif");
	
%>

<label>Today - <%=currentWeather.getDescription() %></label>
<liferay-ui:error key="error" message="weather.error" />

<liferay-ui:search-container emptyResultsMessage="Empty">
    <liferay-ui:search-container-results results="<%= temp %>"
        total="<%= temp.size() %>" 
    />
	
    <liferay-ui:search-container-row
        className="Weather"
        modelVar="weather" escapedModel="<%= true %>" >
        
        <liferay-ui:search-container-column-text>
			<img src="<%=request.getContextPath()%>/images/<%=imgName%>" />

		</liferay-ui:search-container-column-text>
         
        <liferay-ui:search-container-column-text
            name="weather.temperature"
            property="temperature"/>
            
         <liferay-ui:search-container-column-text
            name="weather.pressure"
            property="pressure"/>
            
         <liferay-ui:search-container-column-text
            name="weather.humidity"
            property="humidity"/>
            
         <liferay-ui:search-container-column-text
            name="weather.windSpeed"
            property="windSpeed"/>
       
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator paginate="<%=false%>" />

</liferay-ui:search-container>