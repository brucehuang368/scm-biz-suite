
package com.doublechaintech.retailscm.interviewtype;















import com.doublechaintech.retailscm.*;import com.doublechaintech.retailscm.BaseViewPage;import com.doublechaintech.retailscm.RetailscmUserContextImpl;import com.doublechaintech.retailscm.employee.Employee;import com.doublechaintech.retailscm.employeeinterview.EmployeeInterview;import com.doublechaintech.retailscm.iamservice.*;import com.doublechaintech.retailscm.interviewtype.InterviewType;import com.doublechaintech.retailscm.retailstorecountrycenter.CandidateRetailStoreCountryCenter;import com.doublechaintech.retailscm.retailstorecountrycenter.RetailStoreCountryCenter;import com.doublechaintech.retailscm.secuser.SecUser;import com.doublechaintech.retailscm.services.IamService;import com.doublechaintech.retailscm.tree.*;import com.doublechaintech.retailscm.treenode.*;import com.doublechaintech.retailscm.userapp.UserApp;import com.doublechaintech.retailscm.utils.ModelAssurance;
import com.terapico.caf.BlobObject;import com.terapico.caf.DateTime;import com.terapico.caf.Images;import com.terapico.caf.Password;import com.terapico.caf.baseelement.PlainText;import com.terapico.caf.viewpage.SerializeScope;
import com.terapico.uccaf.BaseUserContext;
import com.terapico.utils.*;
import java.math.BigDecimal;
import java.util.*;
import com.doublechaintech.retailscm.search.Searcher;


public class InterviewTypeManagerImpl extends CustomRetailscmCheckerManager implements InterviewTypeManager, BusinessHandler{

	// Only some of ods have such function
	
	// To test
	public BlobObject exportExcelFromList(RetailscmUserContext userContext, String id, String listName) throws Exception {

		Map<String,Object> tokens = InterviewTypeTokens.start().withTokenFromListName(listName).done();
		InterviewType  interviewType = (InterviewType) this.loadInterviewType(userContext, id, tokens);
		//to enrich reference object to let it show display name
		List<BaseEntity> entityListToNaming = interviewType.collectRefercencesFromLists();
		interviewTypeDaoOf(userContext).alias(entityListToNaming);

		return exportListToExcel(userContext, interviewType, listName);

	}
	@Override
	public BaseGridViewGenerator gridViewGenerator() {
		return new InterviewTypeGridViewGenerator();
	}
	




  


	private static final String SERVICE_TYPE = "InterviewType";
	@Override
	public InterviewTypeDAO daoOf(RetailscmUserContext userContext) {
		return interviewTypeDaoOf(userContext);
	}

	@Override
	public String serviceFor(){
		return SERVICE_TYPE;
	}



	protected void throwExceptionWithMessage(String value) throws InterviewTypeManagerException{

		Message message = new Message();
		message.setBody(value);
		throw new InterviewTypeManagerException(message);

	}



 	protected InterviewType saveInterviewType(RetailscmUserContext userContext, InterviewType interviewType, String [] tokensExpr) throws Exception{
 		//return getInterviewTypeDAO().save(interviewType, tokens);

 		Map<String,Object>tokens = parseTokens(tokensExpr);

 		return saveInterviewType(userContext, interviewType, tokens);
 	}

 	protected InterviewType saveInterviewTypeDetail(RetailscmUserContext userContext, InterviewType interviewType) throws Exception{


 		return saveInterviewType(userContext, interviewType, allTokens());
 	}

 	public InterviewType loadInterviewType(RetailscmUserContext userContext, String interviewTypeId, String [] tokensExpr) throws Exception{

 		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);

		checkerOf(userContext).throwExceptionIfHasErrors( InterviewTypeManagerException.class);



 		Map<String,Object>tokens = parseTokens(tokensExpr);

 		InterviewType interviewType = loadInterviewType( userContext, interviewTypeId, tokens);
 		//do some calc before sent to customer?
 		return present(userContext,interviewType, tokens);
 	}


 	 public InterviewType searchInterviewType(RetailscmUserContext userContext, String interviewTypeId, String textToSearch,String [] tokensExpr) throws Exception{

 		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);

		checkerOf(userContext).throwExceptionIfHasErrors( InterviewTypeManagerException.class);



 		Map<String,Object>tokens = tokens().allTokens().searchEntireObjectText(tokens().startsWith(), textToSearch).initWithArray(tokensExpr);

 		InterviewType interviewType = loadInterviewType( userContext, interviewTypeId, tokens);
 		//do some calc before sent to customer?
 		return present(userContext,interviewType, tokens);
 	}



 	protected InterviewType present(RetailscmUserContext userContext, InterviewType interviewType, Map<String, Object> tokens) throws Exception {


		addActions(userContext,interviewType,tokens);
    

		InterviewType  interviewTypeToPresent = interviewTypeDaoOf(userContext).present(interviewType, tokens);

		List<BaseEntity> entityListToNaming = interviewTypeToPresent.collectRefercencesFromLists();
		interviewTypeDaoOf(userContext).alias(entityListToNaming);


		renderActionForList(userContext,interviewType,tokens);

		return  interviewTypeToPresent;


	}



 	public InterviewType loadInterviewTypeDetail(RetailscmUserContext userContext, String interviewTypeId) throws Exception{
 		InterviewType interviewType = loadInterviewType( userContext, interviewTypeId, allTokens());
 		return present(userContext,interviewType, allTokens());

 	}

	public Object prepareContextForUserApp(BaseUserContext userContext,Object targetUserApp) throws Exception{
		
        UserApp userApp=(UserApp) targetUserApp;
        return this.view ((RetailscmUserContext)userContext,userApp.getAppId());
        
    }

	


 	public Object view(RetailscmUserContext userContext, String interviewTypeId) throws Exception{
 		InterviewType interviewType = loadInterviewType( userContext, interviewTypeId, viewTokens());
 		markVisited(userContext, interviewType);
 		return present(userContext,interviewType, viewTokens());

	 }
	 public Object summaryView(RetailscmUserContext userContext, String interviewTypeId) throws Exception{
		InterviewType interviewType = loadInterviewType( userContext, interviewTypeId, viewTokens());
		interviewType.summarySuffix();
		markVisited(userContext, interviewType);
 		return present(userContext,interviewType, summaryTokens());

	}
	 public Object analyze(RetailscmUserContext userContext, String interviewTypeId) throws Exception{
		InterviewType interviewType = loadInterviewType( userContext, interviewTypeId, analyzeTokens());
		markVisited(userContext, interviewType);
		return present(userContext,interviewType, analyzeTokens());

	}
 	protected InterviewType saveInterviewType(RetailscmUserContext userContext, InterviewType interviewType, Map<String,Object>tokens) throws Exception{
 	
 		return interviewTypeDaoOf(userContext).save(interviewType, tokens);
 	}
 	protected InterviewType loadInterviewType(RetailscmUserContext userContext, String interviewTypeId, Map<String,Object>tokens) throws Exception{
		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);

		checkerOf(userContext).throwExceptionIfHasErrors( InterviewTypeManagerException.class);



 		return interviewTypeDaoOf(userContext).load(interviewTypeId, tokens);
 	}

	







 	protected<T extends BaseEntity> void addActions(RetailscmUserContext userContext, InterviewType interviewType, Map<String, Object> tokens){
		super.addActions(userContext, interviewType, tokens);

		addAction(userContext, interviewType, tokens,"@create","createInterviewType","createInterviewType/","main","primary");
		addAction(userContext, interviewType, tokens,"@update","updateInterviewType","updateInterviewType/"+interviewType.getId()+"/","main","primary");
		addAction(userContext, interviewType, tokens,"@copy","cloneInterviewType","cloneInterviewType/"+interviewType.getId()+"/","main","primary");

		addAction(userContext, interviewType, tokens,"interview_type.transfer_to_company","transferToAnotherCompany","transferToAnotherCompany/"+interviewType.getId()+"/","main","primary");
		addAction(userContext, interviewType, tokens,"interview_type.addEmployeeInterview","addEmployeeInterview","addEmployeeInterview/"+interviewType.getId()+"/","employeeInterviewList","primary");
		addAction(userContext, interviewType, tokens,"interview_type.removeEmployeeInterview","removeEmployeeInterview","removeEmployeeInterview/"+interviewType.getId()+"/","employeeInterviewList","primary");
		addAction(userContext, interviewType, tokens,"interview_type.updateEmployeeInterview","updateEmployeeInterview","updateEmployeeInterview/"+interviewType.getId()+"/","employeeInterviewList","primary");
		addAction(userContext, interviewType, tokens,"interview_type.copyEmployeeInterviewFrom","copyEmployeeInterviewFrom","copyEmployeeInterviewFrom/"+interviewType.getId()+"/","employeeInterviewList","primary");






	}// end method of protected<T extends BaseEntity> void addActions(RetailscmUserContext userContext, InterviewType interviewType, Map<String, Object> tokens){








  @Override
  public List<InterviewType> searchInterviewTypeList(RetailscmUserContext ctx, InterviewTypeRequest pRequest){
      pRequest.setUserContext(ctx);
      List<InterviewType> list = daoOf(ctx).search(pRequest);
      Searcher.enhance(list, pRequest);
      return list;
  }

  @Override
  public InterviewType searchInterviewType(RetailscmUserContext ctx, InterviewTypeRequest pRequest){
    pRequest.limit(0, 1);
    List<InterviewType> list = searchInterviewTypeList(ctx, pRequest);
    if (list == null || list.isEmpty()){
      return null;
    }
    return list.get(0);
  }

	public InterviewType createInterviewType(RetailscmUserContext userContext, String code,String companyId,String description,String detailDescription) throws Exception
	{





		checkerOf(userContext).checkCodeOfInterviewType(code);
		checkerOf(userContext).checkDescriptionOfInterviewType(description);
		checkerOf(userContext).checkDetailDescriptionOfInterviewType(detailDescription);


		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);



		InterviewType interviewType=createNewInterviewType();	

		interviewType.setCode(code);
			
		RetailStoreCountryCenter company = loadRetailStoreCountryCenter(userContext, companyId,emptyOptions());
		interviewType.setCompany(company);
		
		
		interviewType.setDescription(description);
		interviewType.setDetailDescription(detailDescription);

		interviewType = saveInterviewType(userContext, interviewType, emptyOptions());
		
		onNewInstanceCreated(userContext, interviewType);
		return interviewType;


	}
	protected InterviewType createNewInterviewType()
	{

		return new InterviewType();
	}

	protected void checkParamsForUpdatingInterviewType(RetailscmUserContext userContext,String interviewTypeId, int interviewTypeVersion, String property, String newValueExpr,String [] tokensExpr)throws Exception
	{
		



		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);
		checkerOf(userContext).checkVersionOfInterviewType( interviewTypeVersion);


		if(InterviewType.CODE_PROPERTY.equals(property)){
		
			checkerOf(userContext).checkCodeOfInterviewType(parseString(newValueExpr));
		

		}

		
		if(InterviewType.DESCRIPTION_PROPERTY.equals(property)){
		
			checkerOf(userContext).checkDescriptionOfInterviewType(parseString(newValueExpr));
		

		}
		if(InterviewType.DETAIL_DESCRIPTION_PROPERTY.equals(property)){
		
			checkerOf(userContext).checkDetailDescriptionOfInterviewType(parseString(newValueExpr));
		

		}


		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);



	}



	public InterviewType clone(RetailscmUserContext userContext, String fromInterviewTypeId) throws Exception{

		return interviewTypeDaoOf(userContext).clone(fromInterviewTypeId, this.allTokens());
	}

	public InterviewType internalSaveInterviewType(RetailscmUserContext userContext, InterviewType interviewType) throws Exception
	{
		return internalSaveInterviewType(userContext, interviewType, allTokens());

	}
	public InterviewType internalSaveInterviewType(RetailscmUserContext userContext, InterviewType interviewType, Map<String,Object> options) throws Exception
	{
		//checkParamsForUpdatingInterviewType(userContext, interviewTypeId, interviewTypeVersion, property, newValueExpr, tokensExpr);


		synchronized(interviewType){
			//will be good when the interviewType loaded from this JVM process cache.
			//also good when there is a ram based DAO implementation
			//make changes to InterviewType.
			if (interviewType.isChanged()){
			
			}

      //checkerOf(userContext).checkAndFixInterviewType(interviewType);
			interviewType = saveInterviewType(userContext, interviewType, options);
			return interviewType;

		}

	}

	public InterviewType updateInterviewType(RetailscmUserContext userContext,String interviewTypeId, int interviewTypeVersion, String property, String newValueExpr,String [] tokensExpr) throws Exception
	{
		checkParamsForUpdatingInterviewType(userContext, interviewTypeId, interviewTypeVersion, property, newValueExpr, tokensExpr);



		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, allTokens());
		if(interviewType.getVersion() != interviewTypeVersion){
			String message = "The target version("+interviewType.getVersion()+") is not equals to version("+interviewTypeVersion+") provided";
			throwExceptionWithMessage(message);
		}
		synchronized(interviewType){
			//will be good when the interviewType loaded from this JVM process cache.
			//also good when there is a ram based DAO implementation
			//make changes to InterviewType.
			
			interviewType.changeProperty(property, newValueExpr);
			interviewType = saveInterviewType(userContext, interviewType, tokens().done());
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
			//return saveInterviewType(userContext, interviewType, tokens().done());
		}

	}

	public InterviewType updateInterviewTypeProperty(RetailscmUserContext userContext,String interviewTypeId, int interviewTypeVersion, String property, String newValueExpr,String [] tokensExpr) throws Exception
	{
		checkParamsForUpdatingInterviewType(userContext, interviewTypeId, interviewTypeVersion, property, newValueExpr, tokensExpr);

		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, allTokens());
		if(interviewType.getVersion() != interviewTypeVersion){
			String message = "The target version("+interviewType.getVersion()+") is not equals to version("+interviewTypeVersion+") provided";
			throwExceptionWithMessage(message);
		}
		synchronized(interviewType){
			//will be good when the interviewType loaded from this JVM process cache.
			//also good when there is a ram based DAO implementation
			//make changes to InterviewType.

			interviewType.changeProperty(property, newValueExpr);
			
			interviewType = saveInterviewType(userContext, interviewType, tokens().done());
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
			//return saveInterviewType(userContext, interviewType, tokens().done());
		}

	}
	protected Map<String,Object> emptyOptions(){
		return tokens().done();
	}

	protected InterviewTypeTokens tokens(){
		return InterviewTypeTokens.start();
	}
	protected Map<String,Object> parseTokens(String [] tokensExpr){
		return tokens().initWithArray(tokensExpr);
	}
	protected Map<String,Object> allTokens(){
		return InterviewTypeTokens.all();
	}
	protected Map<String,Object> analyzeTokens(){
		return tokens().allTokens().analyzeAllLists().done();
	}
	protected Map<String,Object> summaryTokens(){
		return tokens().allTokens().done();
	}
	protected Map<String,Object> viewTokens(){
		return tokens().allTokens()
		.sortEmployeeInterviewListWith(EmployeeInterview.ID_PROPERTY,sortDesc())
		.done();

	}
	protected Map<String,Object> mergedAllTokens(String []tokens){
		return InterviewTypeTokens.mergeAll(tokens).done();
	}
	
	protected void checkParamsForTransferingAnotherCompany(RetailscmUserContext userContext, String interviewTypeId, String anotherCompanyId) throws Exception
 	{

 		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);
 		checkerOf(userContext).checkIdOfRetailStoreCountryCenter(anotherCompanyId);//check for optional reference

 		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);

 	}
 	public InterviewType transferToAnotherCompany(RetailscmUserContext userContext, String interviewTypeId, String anotherCompanyId) throws Exception
 	{
 		checkParamsForTransferingAnotherCompany(userContext, interviewTypeId,anotherCompanyId);
 
		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, allTokens());
		synchronized(interviewType){
			//will be good when the interviewType loaded from this JVM process cache.
			//also good when there is a ram based DAO implementation
			RetailStoreCountryCenter company = loadRetailStoreCountryCenter(userContext, anotherCompanyId, emptyOptions());
			interviewType.updateCompany(company);
			
			interviewType = saveInterviewType(userContext, interviewType, emptyOptions());

			return present(userContext,interviewType, allTokens());

		}

 	}

	


	public CandidateRetailStoreCountryCenter requestCandidateCompany(RetailscmUserContext userContext, String ownerClass, String id, String filterKey, int pageNo) throws Exception {

		CandidateRetailStoreCountryCenter result = new CandidateRetailStoreCountryCenter();
		result.setOwnerClass(ownerClass);
		result.setOwnerId(id);
		result.setFilterKey(filterKey==null?"":filterKey.trim());
		result.setPageNo(pageNo);
		result.setValueFieldName("id");
		result.setDisplayFieldName("name");

		pageNo = Math.max(1, pageNo);
		int pageSize = 20;
		//requestCandidateProductForSkuAsOwner
		SmartList<RetailStoreCountryCenter> candidateList = retailStoreCountryCenterDaoOf(userContext).requestCandidateRetailStoreCountryCenterForInterviewType(userContext,ownerClass, id, filterKey, pageNo, pageSize);
		result.setCandidates(candidateList);
		int totalCount = candidateList.getTotalCount();
		result.setTotalPage(Math.max(1, (totalCount + pageSize -1)/pageSize ));
		return result;
	}

 //--------------------------------------------------------------
	

 	protected RetailStoreCountryCenter loadRetailStoreCountryCenter(RetailscmUserContext userContext, String newCompanyId, Map<String,Object> options) throws Exception
 	{
    
 		return retailStoreCountryCenterDaoOf(userContext).load(newCompanyId, options);
 	  
 	}
 	


	
	//--------------------------------------------------------------

	public void delete(RetailscmUserContext userContext, String interviewTypeId, int interviewTypeVersion) throws Exception {
		//deleteInternal(userContext, interviewTypeId, interviewTypeVersion);
	}
	protected void deleteInternal(RetailscmUserContext userContext,
			String interviewTypeId, int interviewTypeVersion) throws Exception{

		interviewTypeDaoOf(userContext).delete(interviewTypeId, interviewTypeVersion);
	}

	public InterviewType forgetByAll(RetailscmUserContext userContext, String interviewTypeId, int interviewTypeVersion) throws Exception {
		return forgetByAllInternal(userContext, interviewTypeId, interviewTypeVersion);
	}
	protected InterviewType forgetByAllInternal(RetailscmUserContext userContext,
			String interviewTypeId, int interviewTypeVersion) throws Exception{

		return interviewTypeDaoOf(userContext).disconnectFromAll(interviewTypeId, interviewTypeVersion);
	}




	public int deleteAll(RetailscmUserContext userContext, String secureCode) throws Exception
	{
		/*
		if(!("dElEtEaLl".equals(secureCode))){
			throw new InterviewTypeManagerException("Your secure code is not right, please guess again");
		}
		return deleteAllInternal(userContext);
		*/
		return 0;
	}


	protected int deleteAllInternal(RetailscmUserContext userContext) throws Exception{
		return interviewTypeDaoOf(userContext).deleteAll();
	}





	protected void checkParamsForAddingEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId, String employeeId, String remark,String [] tokensExpr) throws Exception{

				checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);


		checkerOf(userContext).checkEmployeeIdOfEmployeeInterview(employeeId);

		checkerOf(userContext).checkRemarkOfEmployeeInterview(remark);


		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);



	}
	public  InterviewType addEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId, String employeeId, String remark, String [] tokensExpr) throws Exception
	{
		checkParamsForAddingEmployeeInterview(userContext,interviewTypeId,employeeId, remark,tokensExpr);

		EmployeeInterview employeeInterview = createEmployeeInterview(userContext,employeeId, remark);

		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, emptyOptions());
		synchronized(interviewType){
			//Will be good when the interviewType loaded from this JVM process cache.
			//Also good when there is a RAM based DAO implementation
			interviewType.addEmployeeInterview( employeeInterview );
			interviewType = saveInterviewType(userContext, interviewType, tokens().withEmployeeInterviewList().done());
			
			employeeInterviewManagerOf(userContext).onNewInstanceCreated(userContext, employeeInterview);
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
		}
	}
	protected void checkParamsForUpdatingEmployeeInterviewProperties(RetailscmUserContext userContext, String interviewTypeId,String id,String remark,String [] tokensExpr) throws Exception {

		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);
		checkerOf(userContext).checkIdOfEmployeeInterview(id);

		checkerOf(userContext).checkRemarkOfEmployeeInterview( remark);


		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);


	}
	public  InterviewType updateEmployeeInterviewProperties(RetailscmUserContext userContext, String interviewTypeId, String id,String remark, String [] tokensExpr) throws Exception
	{
		checkParamsForUpdatingEmployeeInterviewProperties(userContext,interviewTypeId,id,remark,tokensExpr);

		Map<String, Object> options = tokens()
				.allTokens()
				//.withEmployeeInterviewListList()
				.searchEmployeeInterviewListWith(EmployeeInterview.ID_PROPERTY, tokens().is(), id).done();

		InterviewType interviewTypeToUpdate = loadInterviewType(userContext, interviewTypeId, options);

		if(interviewTypeToUpdate.getEmployeeInterviewList().isEmpty()){
			throw new InterviewTypeManagerException("EmployeeInterview is NOT FOUND with id: '"+id+"'");
		}

		EmployeeInterview item = interviewTypeToUpdate.getEmployeeInterviewList().first();
		beforeUpdateEmployeeInterviewProperties(userContext,item, interviewTypeId,id,remark,tokensExpr);
		item.updateRemark( remark );


		//checkParamsForAddingEmployeeInterview(userContext,interviewTypeId,name, code, used,tokensExpr);
		InterviewType interviewType = saveInterviewType(userContext, interviewTypeToUpdate, tokens().withEmployeeInterviewList().done());
		synchronized(interviewType){
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
		}
	}

	protected  void beforeUpdateEmployeeInterviewProperties(RetailscmUserContext userContext, EmployeeInterview item, String interviewTypeId, String id,String remark, String [] tokensExpr)
						throws Exception {
			// by default, nothing to do
	}

	protected EmployeeInterview createEmployeeInterview(RetailscmUserContext userContext, String employeeId, String remark) throws Exception{

		EmployeeInterview employeeInterview = new EmployeeInterview();
		
		
		Employee  employee = new Employee();
		employee.setId(employeeId);		
		employeeInterview.setEmployee(employee);		
		employeeInterview.setRemark(remark);
	
		
		return employeeInterview;


	}

	protected EmployeeInterview createIndexedEmployeeInterview(String id, int version){

		EmployeeInterview employeeInterview = new EmployeeInterview();
		employeeInterview.setId(id);
		employeeInterview.setVersion(version);
		return employeeInterview;

	}

	protected void checkParamsForRemovingEmployeeInterviewList(RetailscmUserContext userContext, String interviewTypeId,
			String employeeInterviewIds[],String [] tokensExpr) throws Exception {

		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);
		for(String employeeInterviewIdItem: employeeInterviewIds){
			checkerOf(userContext).checkIdOfEmployeeInterview(employeeInterviewIdItem);
		}


		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);

	}
	public  InterviewType removeEmployeeInterviewList(RetailscmUserContext userContext, String interviewTypeId,
			String employeeInterviewIds[],String [] tokensExpr) throws Exception{

			checkParamsForRemovingEmployeeInterviewList(userContext, interviewTypeId,  employeeInterviewIds, tokensExpr);


			InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, allTokens());
			synchronized(interviewType){
				//Will be good when the interviewType loaded from this JVM process cache.
				//Also good when there is a RAM based DAO implementation
				interviewTypeDaoOf(userContext).planToRemoveEmployeeInterviewList(interviewType, employeeInterviewIds, allTokens());
				interviewType = saveInterviewType(userContext, interviewType, tokens().withEmployeeInterviewList().done());
				deleteRelationListInGraph(userContext, interviewType.getEmployeeInterviewList());
				return present(userContext,interviewType, mergedAllTokens(tokensExpr));
			}
	}

	protected void checkParamsForRemovingEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId,
		String employeeInterviewId, int employeeInterviewVersion,String [] tokensExpr) throws Exception{
		
		checkerOf(userContext).checkIdOfInterviewType( interviewTypeId);
		checkerOf(userContext).checkIdOfEmployeeInterview(employeeInterviewId);
		checkerOf(userContext).checkVersionOfEmployeeInterview(employeeInterviewVersion);

		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);


	}
	public  InterviewType removeEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId,
		String employeeInterviewId, int employeeInterviewVersion,String [] tokensExpr) throws Exception{

		checkParamsForRemovingEmployeeInterview(userContext,interviewTypeId, employeeInterviewId, employeeInterviewVersion,tokensExpr);

		EmployeeInterview employeeInterview = createIndexedEmployeeInterview(employeeInterviewId, employeeInterviewVersion);
		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, allTokens());
		synchronized(interviewType){
			//Will be good when the interviewType loaded from this JVM process cache.
			//Also good when there is a RAM based DAO implementation
			interviewType.removeEmployeeInterview( employeeInterview );
			interviewType = saveInterviewType(userContext, interviewType, tokens().withEmployeeInterviewList().done());
			deleteRelationInGraph(userContext, employeeInterview);
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
		}


	}
	protected void checkParamsForCopyingEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId,
		String employeeInterviewId, int employeeInterviewVersion,String [] tokensExpr) throws Exception{
		
		checkerOf(userContext).checkIdOfInterviewType( interviewTypeId);
		checkerOf(userContext).checkIdOfEmployeeInterview(employeeInterviewId);
		checkerOf(userContext).checkVersionOfEmployeeInterview(employeeInterviewVersion);

		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);


	}
	public  InterviewType copyEmployeeInterviewFrom(RetailscmUserContext userContext, String interviewTypeId,
		String employeeInterviewId, int employeeInterviewVersion,String [] tokensExpr) throws Exception{

		checkParamsForCopyingEmployeeInterview(userContext,interviewTypeId, employeeInterviewId, employeeInterviewVersion,tokensExpr);

		EmployeeInterview employeeInterview = createIndexedEmployeeInterview(employeeInterviewId, employeeInterviewVersion);
		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, allTokens());
		synchronized(interviewType){
			//Will be good when the interviewType loaded from this JVM process cache.
			//Also good when there is a RAM based DAO implementation

			

			interviewType.copyEmployeeInterviewFrom( employeeInterview );
			interviewType = saveInterviewType(userContext, interviewType, tokens().withEmployeeInterviewList().done());
			
			employeeInterviewManagerOf(userContext).onNewInstanceCreated(userContext, (EmployeeInterview)interviewType.getFlexiableObjects().get(BaseEntity.COPIED_CHILD));
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
		}

	}

	protected void checkParamsForUpdatingEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId, String employeeInterviewId, int employeeInterviewVersion, String property, String newValueExpr,String [] tokensExpr) throws Exception{
		


		checkerOf(userContext).checkIdOfInterviewType(interviewTypeId);
		checkerOf(userContext).checkIdOfEmployeeInterview(employeeInterviewId);
		checkerOf(userContext).checkVersionOfEmployeeInterview(employeeInterviewVersion);


		if(EmployeeInterview.REMARK_PROPERTY.equals(property)){
			checkerOf(userContext).checkRemarkOfEmployeeInterview(parseString(newValueExpr));
		}
		


		checkerOf(userContext).throwExceptionIfHasErrors(InterviewTypeManagerException.class);


	}

	public  InterviewType updateEmployeeInterview(RetailscmUserContext userContext, String interviewTypeId, String employeeInterviewId, int employeeInterviewVersion, String property, String newValueExpr,String [] tokensExpr)
			throws Exception{

		checkParamsForUpdatingEmployeeInterview(userContext, interviewTypeId, employeeInterviewId, employeeInterviewVersion, property, newValueExpr,  tokensExpr);

		Map<String,Object> loadTokens = this.tokens().withEmployeeInterviewList().searchEmployeeInterviewListWith(EmployeeInterview.ID_PROPERTY, tokens().equals(), employeeInterviewId).done();



		InterviewType interviewType = loadInterviewType(userContext, interviewTypeId, loadTokens);

		synchronized(interviewType){
			//Will be good when the interviewType loaded from this JVM process cache.
			//Also good when there is a RAM based DAO implementation
			//interviewType.removeEmployeeInterview( employeeInterview );
			//make changes to AcceleraterAccount.
			EmployeeInterview employeeInterviewIdVersionKey = createIndexedEmployeeInterview(employeeInterviewId, employeeInterviewVersion);

			EmployeeInterview employeeInterview = interviewType.findTheEmployeeInterview(employeeInterviewIdVersionKey);
			if(employeeInterview == null){
				throw new InterviewTypeManagerException(employeeInterviewId+" is NOT FOUND" );
			}

			beforeUpdateEmployeeInterview(userContext, employeeInterview, interviewTypeId, employeeInterviewId, employeeInterviewVersion, property, newValueExpr,  tokensExpr);
			employeeInterview.changeProperty(property, newValueExpr);
			
			interviewType = saveInterviewType(userContext, interviewType, tokens().withEmployeeInterviewList().done());
			employeeInterviewManagerOf(userContext).onUpdated(userContext, employeeInterview, this, "updateEmployeeInterview");
			return present(userContext,interviewType, mergedAllTokens(tokensExpr));
		}

	}

	/** if you has something need to do before update data from DB, override this */
	protected void beforeUpdateEmployeeInterview(RetailscmUserContext userContext, EmployeeInterview existed, String interviewTypeId, String employeeInterviewId, int employeeInterviewVersion, String property, String newValueExpr,String [] tokensExpr)
  			throws Exception{
  }
	/*

	*/




	public void onNewInstanceCreated(RetailscmUserContext userContext, InterviewType newCreated) throws Exception{
		ensureRelationInGraph(userContext, newCreated);
		sendCreationEvent(userContext, newCreated);

    
	}

  
  

  public void sendAllItems(RetailscmUserContext ctx) throws Exception{
    interviewTypeDaoOf(ctx).loadAllAsStream().forEach(
          event -> sendInitEvent(ctx, event)
    );
  }



	// -----------------------------------//  登录部分处理 \\-----------------------------------
	@Override
  protected BusinessHandler getLoginProcessBizHandler(RetailscmUserContextImpl userContext) {
    return this;
  }

	public void onAuthenticationFailed(RetailscmUserContext userContext, LoginContext loginContext,
			LoginResult loginResult, IdentificationHandler idHandler, BusinessHandler bizHandler)
			throws Exception {
		// by default, failed is failed, nothing can do
	}
	// when user authenticated success, but no sec_user related, this maybe a new user login from 3-rd party service.
	public void onAuthenticateNewUserLogged(RetailscmUserContext userContext, LoginContext loginContext,
			LoginResult loginResult, IdentificationHandler idHandler, BusinessHandler bizHandler)
			throws Exception {
		// Generally speaking, when authenticated user logined, we will create a new account for him/her.
		// you need do it like :
		// First, you should create new data such as:
		//   InterviewType newInterviewType = this.createInterviewType(userContext, ...
		// Next, create a sec-user in your business way:
		//   SecUser secUser = secUserManagerOf(userContext).createSecUser(userContext, login, mobile ...
		// And set it into loginContext:
		//   loginContext.getLoginTarget().setSecUser(secUser);
		// Next, create an user-app to connect secUser and newInterviewType
		//   UserApp uerApp = userAppManagerOf(userContext).createUserApp(userContext, secUser.getId(), ...
		// Also, set it into loginContext:
		//   loginContext.getLoginTarget().setUserApp(userApp);
		// and in most case, this should be considered as "login success"
		//   loginResult.setSuccess(true);
		//
		// Since many of detailed info were depending business requirement, So,
		throw new Exception("请重载函数onAuthenticateNewUserLogged()以处理新用户登录");
	}
	protected SmartList<UserApp> getRelatedUserAppList(RetailscmUserContext userContext, SecUser secUser) {
    MultipleAccessKey key = new MultipleAccessKey();
    key.put(UserApp.SEC_USER_PROPERTY, secUser.getId());
    key.put(UserApp.APP_TYPE_PROPERTY, InterviewType.INTERNAL_TYPE);
    SmartList<UserApp> userApps = userContext.getDAOGroup().getUserAppDAO().findUserAppWithKey(key, EO);
    return userApps;
  }
	// -----------------------------------\\  登录部分处理 //-----------------------------------



	// -----------------------------------// list-of-view 处理 \\-----------------------------------
    protected void enhanceForListOfView(RetailscmUserContext userContext,SmartList<InterviewType> list) throws Exception {
    	if (list == null || list.isEmpty()){
    		return;
    	}
		List<RetailStoreCountryCenter> companyList = RetailscmBaseUtils.collectReferencedObjectWithType(userContext, list, RetailStoreCountryCenter.class);
		userContext.getDAOGroup().enhanceList(companyList, RetailStoreCountryCenter.class);


    }
	
	public Object listByCompany(RetailscmUserContext userContext,String companyId) throws Exception {
		return listPageByCompany(userContext, companyId, 0, 20);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object listPageByCompany(RetailscmUserContext userContext,String companyId, int start, int count) throws Exception {
		SmartList<InterviewType> list = interviewTypeDaoOf(userContext).findInterviewTypeByCompany(companyId, start, count, new HashMap<>());
		enhanceForListOfView(userContext, list);
		RetailscmCommonListOfViewPage page = new RetailscmCommonListOfViewPage();
		page.setClassOfList(InterviewType.class);
		page.setContainerObject(RetailStoreCountryCenter.withId(companyId));
		page.setRequestBeanName(this.getBeanName());
		page.setDataList((SmartList)list);
		page.setPageTitle("面试类型列表");
		page.setRequestName("listByCompany");
		page.setRequestOffset(start);
		page.setRequestLimit(count);
		page.setDisplayMode("auto");
		page.setLinkToUrl(TextUtil.encodeUrl(String.format("%s/listByCompany/%s/",  getBeanName(), companyId)));

		page.assemblerContent(userContext, "listByCompany");
		return page.doRender(userContext);
	}
  
  // -----------------------------------\\ list-of-view 处理 //-----------------------------------v
  
 	/**
	 * miniprogram调用返回固定的detail class
	 *
	 * @return
	 * @throws Exception
	 */
 	public Object wxappview(RetailscmUserContext userContext, String interviewTypeId) throws Exception{
    SerializeScope vscope = SerializeScope.EXCLUDE().nothing();
		InterviewType merchantObj = (InterviewType) this.view(userContext, interviewTypeId);
    String merchantObjId = interviewTypeId;
    String linkToUrl =	"interviewTypeManager/wxappview/" + merchantObjId + "/";
    String pageTitle = "面试类型"+"详情";
		Map result = new HashMap();
		List propList = new ArrayList();
		List sections = new ArrayList();
 
		propList.add(
				MapUtil.put("id", "1-id")
				    .put("fieldName", "id")
				    .put("label", "ID")
				    .put("type", "text")
				    .put("linkToUrl", "")
				    .put("displayMode", "{}")
				    .into_map()
		);
		result.put("id", merchantObj.getId());

		propList.add(
				MapUtil.put("id", "2-code")
				    .put("fieldName", "code")
				    .put("label", "代码")
				    .put("type", "text")
				    .put("linkToUrl", "")
				    .put("displayMode", "{}")
				    .into_map()
		);
		result.put("code", merchantObj.getCode());

		propList.add(
				MapUtil.put("id", "3-company")
				    .put("fieldName", "company")
				    .put("label", "公司")
				    .put("type", "auto")
				    .put("linkToUrl", "retailStoreCountryCenterManager/wxappview/:id/")
				    .put("displayMode", "{\"brief\":\"description\",\"imageUrl\":\"\",\"name\":\"auto\",\"title\":\"name\",\"imageList\":\"\"}")
				    .into_map()
		);
		result.put("company", merchantObj.getCompany());

		propList.add(
				MapUtil.put("id", "4-description")
				    .put("fieldName", "description")
				    .put("label", "描述")
				    .put("type", "text")
				    .put("linkToUrl", "")
				    .put("displayMode", "{}")
				    .into_map()
		);
		result.put("description", merchantObj.getDescription());

		propList.add(
				MapUtil.put("id", "5-detailDescription")
				    .put("fieldName", "detailDescription")
				    .put("label", "详细描述")
				    .put("type", "text")
				    .put("linkToUrl", "")
				    .put("displayMode", "{}")
				    .into_map()
		);
		result.put("detailDescription", merchantObj.getDetailDescription());

		//处理 sectionList

		//处理Section：employeeInterviewListSection
		Map employeeInterviewListSection = ListofUtils.buildSection(
		    "employeeInterviewListSection",
		    "员工面试名单",
		    null,
		    "",
		    "__no_group",
		    "employeeInterviewManager/listByInterviewType/"+merchantObjId+"/",
		    "auto"
		);
		sections.add(employeeInterviewListSection);

		result.put("employeeInterviewListSection", ListofUtils.toShortList(merchantObj.getEmployeeInterviewList(), "employeeInterview"));

		result.put("propList", propList);
		result.put("sectionList", sections);
		result.put("pageTitle", pageTitle);
		result.put("linkToUrl", linkToUrl);

		vscope.field("propList", SerializeScope.EXCLUDE())
				.field("sectionList", SerializeScope.EXCLUDE())
				.field("pageTitle", SerializeScope.EXCLUDE())
				.field("linkToUrl", SerializeScope.EXCLUDE());
		userContext.forceResponseXClassHeader("com.terapico.appview.DetailPage");
		return BaseViewPage.serialize(result, vscope);
	}

  










}




