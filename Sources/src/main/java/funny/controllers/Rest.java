package funny.controllers;

import funny.models.ModelSchedules;
import funny.models.RestModel;
import org.jbpm.bpmn2.handler.ServiceTaskHandler;
import org.kie.api.KieBase;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.process.WorkflowProcessInstance;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tony on 23.05.2016.
 */
@RestController
public class Rest {

    @RequestMapping("/tasks/activatestaff")
    public RestModel activatestaff(Model model, @RequestParam(value="id",required = true) String id) throws Exception {
        int intId = Integer.parseInt(id);
        KieBase kbase = createKnowledgeBase();
        KieSession ksession = kbase.newKieSession();
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task", new ServiceTaskHandler());
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", intId);
        params.put("active", false);
        params.put("model", 1);
        WorkflowProcessInstance processInstance = (WorkflowProcessInstance)
                ksession.startProcess("FunnyProcess",params);
        return new RestModel((Boolean)processInstance.getVariable("active"));
    }

    private static KieBase createKnowledgeBase() throws Exception {

        KieHelper kieHelper = new KieHelper();
        KieBase kieBase = kieHelper.addResource(ResourceFactory.newClassPathResource("sample.bpmn2"))
                .build();
        return kieBase;

    }

}
