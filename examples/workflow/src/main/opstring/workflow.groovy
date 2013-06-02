
import org.rioproject.config.Constants

deployment(name: 'Workflow Example') {
    groups System.getProperty(Constants.GROUPS_PROPERTY_NAME, System.getProperty('user.name'))

    /* The implementation and client artifacts are defined globally and referenced
     * in each service bean declaration */
    artifact id: 'service-impl',   'org.rioproject.examples.workflow:workflow-service:2.1'
    artifact id: 'service-dl',     'org.rioproject.examples.workflow:workflow-api:2.1'
    artifact id: 'outrigger-dl',   'org.apache.river:outrigger-dl:2.2.1'
    artifact id: 'outrigger-impl', 'org.apache.river:outrigger:2.2.1'

    ['New Worker'    : 'NEW',
     'Pending Worker': 'PENDING',
     'Open Worker'   : 'OPEN',
     'Closed Worker' : 'CLOSED'].each {name, state ->

        service(name: name) {
            implementation(class: "org.rioproject.examples.workflow.Worker") {
                artifact ref: 'service-impl'
            }
            
            parameters {
                parameter name: "template", value: state
            }

            association(type: "requires",
                        serviceType: "net.jini.space.JavaSpace05",
                        property: "javaSpace", name: "Workflow Space")

            maintain 1
        }
    }

    service(name: 'Master') {
        interfaces {
            classes 'org.rioproject.examples.workflow.Master'
            artifact ref: 'service-dl'
        }

        implementation(class: "org.rioproject.examples.workflow.MasterImpl") {
            artifact ref: 'service-impl'
        }

        association(type: "requires",
                    serviceType: "net.jini.space.JavaSpace05",
                    property: "javaSpace", name: "Workflow Space")

        maintain 1
    }

    service(name: 'Workflow Space') {
        interfaces {
            classes 'net.jini.space.JavaSpace05'
            artifact ref: 'outrigger-dl'
        }

        implementation(class: 'com.sun.jini.outrigger.TransientOutriggerImpl') {
            artifact ref: 'outrigger-impl'
        }

        maintain 1

    }

}

