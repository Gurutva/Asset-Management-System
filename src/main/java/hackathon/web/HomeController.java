package hackathon.web;


import java.security.Principal;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hackathon.entities.Asset;
import hackathon.entities.AssetAllocated;
import hackathon.entities.Report;
import hackathon.entities.User;
import hackathon.repositories.AssetAllocatedRepository;
import hackathon.repositories.AssetRepository;
import hackathon.service.AssetServices;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


@Controller
public class HomeController
{	
	@Autowired
	private AssetServices assetServices;
	
	@Autowired
	public JavaMailSender javaMailSender;
	@Autowired
	private AssetAllocatedRepository assetAllocatedRepository;
	
	@GetMapping("/home")
	public String home(Model model)
	{
		return "userhome";
	}
	
	@GetMapping("/admin/assetStatusUpdate/{id}")
    public String showFormForUpdateStatus(@PathVariable ( value = "id") long id, Model model) {
     
     Asset asset = assetServices.getAssetById(id);
     model.addAttribute("asset", asset);
     if(asset.getStatus()==0)
     {
    	 asset.setStatus(1);
     }
     else
     {
    	 asset.setStatus(0);
     }
     assetServices.saveAsset(asset);
     return "redirect:/admin/assetlist";
    }
	

	
	
	
	@GetMapping("/admin/assetlist")
    public String viewHomePage(Model model) {
    	model.addAttribute("listAssets", assetServices.getAllAssets());
    	 return findPaginated(1, "brand", "asc", model);
    }
    @GetMapping("/admin/showNewAssetForm")
    public String showNewAssetForm(Model model) {
        
        Asset asset = new Asset();
        model.addAttribute("asset", asset);
        return "new_asset";
    }

    
    
    @GetMapping("/admin/showFormForAssetUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
     
     Asset asset = assetServices.getAssetById(id);
     model.addAttribute("asset", asset);
     return "update_asset";
    }
    

    

    @GetMapping("/admin/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
        @RequestParam("sortField") String sortField,
        @RequestParam("sortDir") String sortDir,
        Model model) {
        int pageSize = 5;

        Page < Asset > page = assetServices.findPaginated(pageNo, pageSize, sortField, sortDir);
        List < Asset > listAssets = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listAssets", listAssets);
        return "assetlist";
    }
    
    @GetMapping("/admin/employeelist")
    public String viewHomePage1(Model model) {
    	model.addAttribute("listusers", assetServices.getAllUsers());
   	 //return "employeelist";
      	 return findPaginatedUser(1, "name", "asc", model);
    }
    
    @GetMapping("/admin/showNewUserForm")
    public String showNewEmployeeForm(Model model) {
        User user = new User();
        
        model.addAttribute("user", user);
        return "new_employee";
    }
   
    
  
    
    
    @GetMapping("/admin/page1/{pageNo}")
    public String findPaginatedUser(@PathVariable(value = "pageNo") int pageNo,
        @RequestParam("sortField") String sortField,
        @RequestParam("sortDir") String sortDir,
        Model model) {
        int pageSize = 5;

        Page < User > page = assetServices.findPaginatedUser(pageNo, pageSize, sortField, sortDir);
        List < User > listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listusers", listEmployees);
        return "employeelist";
    }
    
    
    @GetMapping("/admin/showFormForAssetAllocation/{id}")
    public String showFormForAssetAllocation(@PathVariable ( value = "id") long id, Model model) {
     
     Asset asset = assetServices.getAssetById(id);
     
     model.addAttribute("asset", asset);
     model.addAttribute("listusers", assetServices.getAllUsers());
     return "allocate_asset";
    }
    
    @PostMapping("/saveAsset")
    public String saveAsset(@ModelAttribute("asset") Asset asset) {
    	assetServices.saveAsset(asset);   	
        return "redirect:/admin/assetlist";
    }
    
    
    
    @PostMapping("/saveAsset1")
    public String saveAsset1(@ModelAttribute("asset") Asset asset) {
    	java.sql.Date date1 = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    	asset.setAllocated_date(date1);
    	AssetAllocated assetAllocated=new AssetAllocated();
    	assetAllocated.setAllocated_date(date1);
    	assetAllocated.setEmployee_id(asset.getAllocated());
    	assetAllocated.setAsset_id(asset.getId());
    	assetAllocated.setExpected_return_date(asset.getExpected_return_date());
    	assetAllocated.setAllocated_date(asset.getAllocated_date());
    	assetAllocated.setStatus(1);
    	assetServices.saveAssetAllocated(assetAllocated);
    	asset.setAllocated_id(assetAllocated.getId());
    	assetServices.saveAsset(asset);
    	
    	
    	List<User> emailid = (List<User>) assetServices.findEmailId(asset.getAllocated());
    	
    	User user=emailid.get(0);
    	String email=user.getEmail();
    	
    	SimpleMailMessage message=new SimpleMailMessage();
		message.setTo(email);//Recipient
		message.setSubject("Asset allocation");//Mail subject
		message.setText("A Asset has been allocated to you. Please Check And Confirm it in Your Dashboard");//Mail body
		javaMailSender.send(message);
		
		
		
        return "redirect:/admin/assetlist";
    }
    
    
    
    
    
    
    
    
    
    
    
     
    //Deallocate Controller Start
   

    @GetMapping("/admin/showDeallocate/{id}")
    public String showDeallocate(@PathVariable ( value = "id") long id, Model model) {
 
     Asset asset = assetServices.getAssetById(id);
     id=asset.getAllocated_id();
//     asset.setAllocated(0);
//     asset.setAllocated_date(null);
//     asset.setExpected_return_date(null);
//     asset.setAllocated_id(0);
     AssetAllocated assetAllocated = assetServices.getAssetAllocatedById(id);
	model.addAttribute("assetAllocated", assetAllocated);
     assetServices.saveAsset(asset);   
		return "deallocate";
    }
    
    @PostMapping("/saveAsset2")
    public String saveAsset2(@ModelAttribute("assetAllocated") AssetAllocated assetAllocated) {
    	java.sql.Date date2 = new java.sql.Date(Calendar.getInstance().getTime().getTime());
    	long id=assetAllocated.getAsset_id();
    	Asset asset = assetServices.getAssetById(id);
    	asset.setAllocated(0);
        asset.setAllocated_date(null);
        asset.setExpected_return_date(null);
        asset.setAllocated_id(0);
    	assetAllocated.setReturn_date(date2);
    	assetAllocated.setStatus(0);
    	assetServices.saveAssetAllocated(assetAllocated);
        return "redirect:/admin/assetlist";
    }
    
  //Deallocate Controller End
    
    
    
    
    
    
    
   // employee controller start
    
 
    
    @GetMapping("/admin/employeeStatusUpdate/{id}")
    public String employeeStatusUpdate(@PathVariable ( value = "id") long id, Model model) {
     
     User user = assetServices.getUserById(id);
     model.addAttribute("user", user);
     if(user.getStatus()==1)
     {
    	 user.setStatus(0);
     }
     else
     {
    	 user.setStatus(1);
     }
     assetServices.saveUser(user);
     return "redirect:/admin/employeelist";
    }
    
    @PostMapping("/admin/saveUser")
    public String saveEmployee(@ModelAttribute("user") User user) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	String encodedPassword = passwordEncoder.encode((user.getPassword()));
    	user.setPassword(encodedPassword);
    	 assetServices.saveUser(user);
        return "redirect:/admin/employeelist";  
    }
    
    @GetMapping("/admin/showFormUserForUpdate/{id}")
    public String showFormUserForUpdate(@PathVariable ( value = "id") long id, Model model) {
     
     User user = assetServices.getUserById(id);
     
     model.addAttribute("user", user);
     return "update_employee";
    }

    
    // employee controller end
    
    
    
    
    
    @GetMapping("/admin/viewTracker/{id}")
    public String viewTracker(@PathVariable ( value = "id") long id, Model model) {
//    	AssetAllocated assetAllocated = assetServices.getAssetAllocatedById(id);
//    	model.addAttribute("listassetAllocated", assetAllocated);
    	List<AssetAllocated> cities = (List<AssetAllocated>) assetServices.findAssetAlloctedDetails(id);
    	model.addAttribute("assetlist", cities);
    	return "asset_tracker";
       }
    
    @GetMapping("/viewProfile")
    public String viewProfile( Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	List<User> employees = (List<User>) assetServices.findEmployeeDetails(auth.getName());
    	model.addAttribute("employee", employees);
//    	User user=employees.get(0);
//    	user.getId
    	return "employee_profile";
       }
    @GetMapping("/viewEmployeeAllocatedAsset")
    public String viewEmployeeAllocatedAsset( Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	List<User> employees = (List<User>) assetServices.findEmployeeDetails(auth.getName());
    	User user=employees.get(0);
    	int emp_id=user.getId();
    	List<AssetAllocated> assetdetails = (List<AssetAllocated>) assetServices.findAssetAlloctedDetailsUsingEmpId(emp_id);
    	model.addAttribute("assetdetails", assetdetails);
    	return "employee_assets_allocated";
       }
    
    
    @GetMapping("/confirmAssetAllocation/{id}")
    public String confirmAssetAllocation(@PathVariable ( value = "id") long id, Model model) {
     
     AssetAllocated assetallocated = assetServices.getAssetAllocatedById(id);
     if(assetallocated.getIs_confirm()==0)
     {
    	 assetallocated.setIs_confirm(1);
     }
     assetServices.saveAssetAllocated(assetallocated);
     return "redirect:/viewEmployeeAllocatedAsset";
    }
    
    @GetMapping("/assetReport/{id}")
    public String assetReport(@PathVariable ( value = "id") long id, Model model) {
    		Report report = new Report();
        
        model.addAttribute("report", report);
        report.setAsset_allocated_id((int)id);
        return "new_report";
    }
    
    @PostMapping("/saveReport")
    public String saveReport(@ModelAttribute("asset") Report report) {
    	assetServices.saveReport(report); 	
        return "redirect:/viewEmployeeAllocatedAsset";
    }
    
    @GetMapping("/admin/viewEmployeeReports")
    public String viewEmployeeReports(Model model) {
    	model.addAttribute("reports",assetServices.getAllReports());
      	return "view_reports";
    }
    
    @GetMapping("/admin/startSurvey")
    public String startSurvey(Model model) {
    	model.addAttribute("assets",assetServices.getAllAssets());
      	return "view_survey";
    }
    @GetMapping("/admin/stopSurvey")
    public String stopSurvey(Model model) {
    	List<Asset> assets = (List<Asset>) assetServices.findAllAssets();
    	for(int i=0;i<assets.size();i++)
    	{
    		Asset asset=assets.get(i);
    		asset.setConfirm_survey(0);
    		assetServices.saveAsset(asset);
    	}
    	
    	 return "redirect:/admin/startSurvey";
    }
    
    @GetMapping("/admin/createSurvey")
    public String createSurvey(Model model) {
    	List<Asset> assets = (List<Asset>) assetServices.findAllAssets();
//    	Asset asset=assets.get(0);
    	long x;
    	String email;
    	for(int i=0;i<assets.size();i++)
    	{
    		Asset asset=assets.get(i);
    		asset.setConfirm_survey(1);
    		x=asset.getAllocated();
    		if(x==0)
    		{
    			SimpleMailMessage message=new SimpleMailMessage();
    			message.setTo("principalhackathon@gmail.com");
    			message.setSubject("Employee Survey Initiated");
    			message.setText("A Survey has been Initiated. Please Check And Confirm asset in Your Dashboard");//Mail body
    			javaMailSender.send(message);
    		}
    		else
    		{
    			 User user = assetServices.getUserById(x);
        		email=user.getEmail();
        		SimpleMailMessage message=new SimpleMailMessage();
        		message.setTo(email);
        		message.setSubject("Employee Survey Initiated");
    			message.setText("A Survey has been Initiated. Please Check And Confirm asset in Your Dashboard");
        		javaMailSender.send(message);
    		}
    		
    		assetServices.saveAsset(asset);
    	}
    	 return "redirect:/admin/startSurvey";
    	 
    }
    
    
    @GetMapping("/viewEmployeeSurvey")
    public String viewEmployeeSurvey(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	List<User> employees = (List<User>) assetServices.findEmployeeDetails(auth.getName());
    	User user2=employees.get(0);
    	int id=user2.getId();
    	
    	model.addAttribute("employeesurveys",assetServices.findEmployeeSurvey(id));
      	return "employee_survey";
    }
    
    @GetMapping("/changeConfirmSurvey/{id}")
    public String changeConfirmSurveyt(@PathVariable ( value = "id") long id, Model model) {
    		Asset asset3=assetServices.getAssetById(id);
    		asset3.setConfirm_survey(2);
    		assetServices.saveAsset(asset3);
    		return "redirect:/viewEmployeeSurvey";
    }
    
    
    @GetMapping("admin/adminEmployeeProfile/{id}")
    public String adminEmployeeProfile(@PathVariable ( value = "id") long id, Model model) {
    		User user2=assetServices.getUserById(id);
    		model.addAttribute("userdetails",user2);
    		return "employeeprofileadmin";
    }
    
    @GetMapping("viewAssetDetailsEmployee/{id}")
    public String viewAssetDetailsEmployee(@PathVariable ( value = "id") long id, Model model) {
    		Asset asset2=assetServices.getAssetById(id);
    		model.addAttribute("assetdetails",asset2);
    		return "assetdetails";
    }
    
    @GetMapping("admin/viewReportAssetDetails/{id}")
    public String viewReportAssetDetails(@PathVariable ( value = "id") long id, Model model) {
    		AssetAllocated assetallocated=assetServices.getAssetAllocatedById(id);
    		model.addAttribute("assetreport",assetallocated);
    		return "asset_allocated_report_details";
    }
    
    
    
  
    
}
