This project collects timer data from nodes and displays as interconnected nodes.

Import this project in eclipse as existing maven project.
Run ApplicationConfiguration.java as java application.
In browser enter http://localhost<port>:/index

This can help in analysis. This can help in identifying and visualising the dependencies, which Services are "heavy and busy” etc
Secondly, this can potentially be used for Instrumentation and visualising improvements ( after some refinement and simplification).
It relies on data gathered from the Service timers and thereby is non-intrusive.

Quick Guide :
        By Default it shows a node with Oscar icon.
            Right Click -> More 
			Click tabs Inbound/Outbound to see all the inbound/outbound calls. The view shows sorted list based on calls. Select the ones you want to visualize and click Add
                OR
            Double Click on the service icon to see all the incoming and outgoing calls to that service
        Single Click to highlight the calls
        Right Click -> View Raw Data to view raw timers data 
        Double Click again on any of the services to “Drill Down”
        Repeat

Note : Currently, AppDymanics can generate nodes and data for individual services but it currently does not let you visualise a drill down.
