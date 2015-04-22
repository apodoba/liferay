<div class="columns-3-2-2" id="main-content" role="main">

        <div class="portlet-layout row-fluid">
                <div class="portlet-column portlet-column-first span8" id="column-1">
                        $processor.processColumn("column-1", "portlet-column-content portlet-column-content-first")
                </div>
                
                <div class="portlet-column portlet-column-last span4" id="column-2">
                		$processor.processPortlet("portalexchange_WAR_portalexchangeportlet")
                        $processor.processColumn("column-2", "portlet-column-content portlet-column-content-last")
                </div>
        </div>
        
        <div class="portlet-layout row-fluid">
                <div class="portlet-column portlet-column-first span7" id="column-3">
                        $processor.processColumn("column-3", "portlet-column-content portlet-column-content-first")
                </div>
                <div class="portlet-column portlet-column-last span5" id="column-4">
                		$processor.processPortlet("portalweather_WAR_portalweatherportlet")
                        $processor.processColumn("column-4", "portlet-column-content portlet-column-content-last")
                </div>
        </div>

        <div class="portlet-layout row-fluid">
                <div class="portlet-column portlet-column-first span7" id="column-5">
                        $processor.processColumn("column-5", "portlet-column-content portlet-column-content-first")
                </div>
                
                <div class="portlet-column portlet-column-last span5" id="column-6">
                        $processor.processColumn("column-6", "portlet-column-content portlet-column-content-last")
                </div>
        </div>
</div>