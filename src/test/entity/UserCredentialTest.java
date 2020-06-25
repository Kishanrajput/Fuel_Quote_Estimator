package test.entity;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.Test;

import entity.UserCredential;
import entity.FuelQuoteClient;
import entity.User;
import entity.fuelQuoteParameters;
import pl.pojo.tester.api.assertion.Method;

class UserCredentialTest {

	@Test
	public void Should_Pass_All_Pojo_Tests_Using_All_Testers() {
		final Class<?> classUnderTest = UserCredential.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
		.areWellImplemented();
	}
	
	@Test
	public void Should_Pass_UsersPojo_Tests_Using_All_Testers() {
		final Class<?> classUnderTest = User.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
		.areWellImplemented();
	}
	
	@Test
	public void Should_Pass_FuelQuoteParametersPojo_Tests_Using_All_Testers() {
		final Class<?> classUnderTest = fuelQuoteParameters.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
		.areWellImplemented();
	}
	
	@Test
	public void Should_Pass_FuelQuoteClient_Pojo_Tests_Using_All_Testers() {
		final Class<?> classUnderTest = FuelQuoteClient.class;
		assertPojoMethodsFor(classUnderTest).testing(Method.GETTER, Method.SETTER, Method.TO_STRING)
		.areWellImplemented();
	}
	
}
