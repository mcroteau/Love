<% def designService = grailsApplication.classLoader.loadClass('xyz.ioc.DesignService').newInstance()
%>
${raw(designService.render(pageInstance))}
