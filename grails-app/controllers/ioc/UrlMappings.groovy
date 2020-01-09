package ioc

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"{ 
            controller = "page"
            action = "home"
        }

        "/pages"{ 
            controller = "page"
            action = "list"
        }

        "/designs"{ 
            controller = "design"
            action = "index"
        }
		
        "/accounts"{ 
            controller = "account"
            action = "index"
        }
        
        "/plans"{ 
            controller = "plan"
            action = "index"
        }

        
        "/my_account"{ 
            controller = "account"
            action = "my"
        }


        "500"(view:"/error")
        "404"(view:"/notFound")
        

        "/page/view/$id?"(controller:"page", action:"view") 
        "/page/ref/$title?"(controller:"page", action:"ref") 
    }

}
