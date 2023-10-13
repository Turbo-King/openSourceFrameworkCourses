class AccessTargetObject {
   //用户本身的权限 
	private List userPrivilege = new ArrayList(); 
	public List getUserPrivilege() { return userPrivilege; } 
	//环绕通知 
	public Object accessMethod(ProceedingJoinPoint joinPoint) throws Throwable { 
		/** 
		 * * 得到目标类的class形式 
		 * * 得到目标方法 */ 
		Class targetClass = joinPoint.getTarget().getClass(); 
		String targetMethodName = joinPoint.getSignature().getName(); 
		String methodAccess = AnnotationParse.parse(targetClass, targetMethodName); 
		boolean flage = false; 
		for (Privilege privilege : userPrivilege) { 
			if(methodAccess.equals(privilege.getName())){ 
				flage = true; break; } 
		} 
		if(flage){ 
			return joinPoint.proceed(); 
		} else { 
			System.out.println("权限不足"); return null; } 
	} 
}