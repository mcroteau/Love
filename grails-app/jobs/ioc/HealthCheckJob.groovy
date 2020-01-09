package ioc

class HealthCheck {
	
    static triggers = {
      	simple startDelay: 4000, repeatInterval: 10000
    }

    void execute() {
    	println "********** BYODO ***********"
    }
	
}
