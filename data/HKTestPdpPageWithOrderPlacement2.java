package com.healthkart.hkAutomation.regression;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.healthkart.enums.PaymentMethod;
import com.healthkart.extentReportUtil.ExtentReportingBaseUtil;
import com.healthkart.hkAutomation.util.GlobalVar;
import com.healthkart.hkAutomation.util.WebDriverUtil;

public class HKTestPdpPageWithOrderPlacement2 extends ExtentReportingBaseUtil {

	private String gatewayOrderId = null;
	private String mob;

	@Test(priority = 0, enabled = true, groups = { "qa" })
	public void HK_Test_OrderPlacementWithoutNavkeyGuestSignUp() {
		System.out.println("order placement without navkey guest login test start..");

		driver.get(GlobalVar.baseAppURL + testdata.get("NavKey_URL"));

		hkPdpStep.QuickBuyOnPdp();

		mob = hkHomeStep.performGuestSignup();

		hkAddressStep.addNewAddressForNewSignUpUser();

		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");

		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();

			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.get(GlobalVar.baseAppURL);
			hkHomeStep.openMyOrders();

			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");

		}
	}

	@Test(priority = 1, dependsOnMethods = "HK_Test_OrderPlacementWithoutNavkeyGuestSignUp", groups = { "qa" })
	public void HK_Test_OrderPlacementWithoutNavkeyLogin() {
		System.out.println("order placement without navkey on pdp page test start..");

		hkHomeStep.performLoginWithMobileOtpAndRemoveCartItem(mob);
		Assert.assertTrue(hkHomeStep.verifyUserLogin(), "User failed to Login");
		driver.get(GlobalVar.baseAppURL + testdata.get("NavKey_URL"));
		hkPdpStep.QuickBuyOnPdp();
		
		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load paymnet page..");
		hkCartStep.removeHKCash();
		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();

			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.get(GlobalVar.baseAppURL);
			hkHomeStep.openMyOrders();

			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");

		}
	}

	@Test(priority = 2, groups = { "qa", "beta" })
	public void HK_Test_OrderPlacementWithoutNavkeyGuestLogin() {
		System.out.println("order placement without navkey guest login test start..");

		driver.get(GlobalVar.baseAppURL + testdata.get("NavKey_URL"));

		hkPdpStep.QuickBuyOnPdp();
		Assert.assertTrue(guestLoginStep.verifyGuestLoginPageLoaded(), "Fail to load Guest Login page..");

		hkHomeStep.performGuestLoginWithMobileOtp(testdata.get("Login_User_Mobile_6"));
		
		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");
		hkCartStep.removeHKCash();

		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();

			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.get(GlobalVar.baseAppURL);
			hkHomeStep.openMyOrders();

			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");

		}

	}

	@Test(priority = 3, groups = { "qa" })
	public void Hk_Test_OrderPlacemnetAddToCartGuestSignup() {
		System.out.println("order placement without navkey add to cart  guest Signup test start..");

		driver.get(GlobalVar.baseAppURL + testdata.get("NavKey_URL"));

		hkPdpStep.addVariantToCart();
		Assert.assertTrue(hkCartStep.verifyCartPageLoaded(),"cart page not loaded");

		hkCartStep.clickProceedToCheckout();

		hkHomeStep.performGuestSignup();

		hkCartStep.clickProceedToCheckoutIfVisible();

		hkAddressStep.addNewAddressForNewSignUpUser();

		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");

		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();

			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.get(GlobalVar.baseAppURL);
			hkHomeStep.openMyOrders();

			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");

		}
	}

	@Test(priority = 4, groups = { "qa", "beta" })
	public void testOrderPlacemnetAddToCartGuestLogin() {
		System.out.println("order placement without navkey add to cart  guest Signup test start..");

		hkHomeStep.performLoginWithMobileOtpAndRemoveCartItem(testdata.get("Login_User_Mobile_6"));
		Assert.assertTrue(hkHomeStep.verifyUserLogin(), "User failed to Login");
		hkHomeStep.userLogout();

		driver.get(GlobalVar.baseAppURL + testdata.get("NavKey_URL"));

		hkPdpStep.addVariantToCart();
		Assert.assertTrue(hkCartStep.verifyCartPageLoaded(),"cart page not loaded");

		hkCartStep.clickProceedToCheckout();
		Assert.assertTrue(guestLoginStep.verifyGuestLoginPageLoaded(), "Fail to load Guest Login page..");

		hkHomeStep.performGuestLoginWithMobileOtp(testdata.get("Login_User_Mobile_6"));
		
		hkCartStep.clickProceedToCheckoutGuest();
		hkAddressStep.selectDeliveryAddress();

		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");
		hkCartStep.removeHKCash();

		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();

			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.get(GlobalVar.baseAppURL);
			hkHomeStep.openMyOrders();

			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");

		}
	}

	@Test(priority = 5, enabled = true, groups = { "qa", "beta" })
	public void testOrderPlaceONBrandCOD() {
		System.out.println("## ORDER PLACE BRAND ON COD WITH  Test starts..");

		hkHomeStep.performLoginWithMobileOtpAndRemoveCartItem(testdata.get("Login_User_Mobile_6"));
		Assert.assertTrue(hkHomeStep.verifyUserLogin(), "User failed to Login");
		driver.get(GlobalVar.baseAppURL + testdata.get("onPdpUrl"));
		hkPdpStep.addVariantToCart();
		Assert.assertTrue(hkCartStep.verifyCartPageLoaded(),"cart page not loaded");
		hkCartStep.removeHKCash();
		hkCartStep.clickProceedToCheckout();

		hkAddressStep.selectDeliveryAddress();

		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();
			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");

			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.navigate().to(GlobalVar.baseAppURL + testdata.get("myorderUrl"));
			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");
		}

	}

	@Test(priority = 6, enabled = true, groups = { "qa", "beta" })
	public void testComboPackQuickBuyAlreadyLoggedInUser() {
		System.out.println("##testComboPackQuickBuyAlreadyLoggedInUser starts..");

		hkHomeStep.performLoginWithMobileOtpAndRemoveCartItem(testdata.get("Login_User_Mobile_6"));
		Assert.assertTrue(hkHomeStep.verifyUserLogin(), "User failed to Login");
		driver.get(GlobalVar.baseAppURL + testdata.get("ComboPack_Url"));
		Assert.assertTrue(hkPdpStep.verifyPDPPageLoaded1(),"pdp page not loaded");
		Assert.assertTrue(hkPdpStep.checkComboPackQuantity(2), "pack quantity mismatch");

		hkPdpStep.QuickBuyOnPdp();
		
		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load paymnet page..");
		hkCartStep.removeHKCash();
		hkPaymentStep.selectPaymentOption(PaymentMethod.INTERNETBANKING, PaymentMethod.STATE_BANK_OF_INDIA,
				PaymentMethod.OUTER);
		hkPaymentStep.clickInternetBankingProceedToPayment();
		if (isQA) {
			hkPaymentStep.markRazorPaySuccess();

			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.get(GlobalVar.baseAppURL);
			hkHomeStep.openMyOrders();

			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);

			Assert.assertTrue(hkMyAcntStep.verifyCancelOrderStatus(gatewayOrderId),
					"Order " + gatewayOrderId + " not found Cancelled for some reason, plz check..");

		}
	}

	@Test(priority = 7, enabled = true, groups = { "qa", "beta" })
	public void testComboPackQuickBuyGuestUser() {
		System.out.println("##testComboPackQuickBuyGuestUser starts..");
		hkHomeStep.performLoginWithMobileOtpAndRemoveCartItem(testdata.get("Login_User_Mobile_6"));
		Assert.assertTrue(hkHomeStep.verifyUserLogin(), "User failed to Login");
		hkHomeStep.userLogout();

		Assert.assertTrue(hkHomeStep.verifyUserLogout(), "User failed to logout");

		driver.get(GlobalVar.baseAppURL + testdata.get("ComboPack_Url"));
		Assert.assertTrue(hkPdpStep.verifyPDPPageLoaded1(),"pdp page not loaded");

		Assert.assertTrue(hkPdpStep.checkComboPackQuantity(2), "pack quantity mismatch");

		hkPdpStep.QuickBuyOnPdp();
		hkHomeStep.performGuestLoginWithMobileOtp(testdata.get("Login_User_Mobile_6"));
		
		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");
		hkCartStep.removeHKCash();
		hkPaymentStep.selectPaymentOption(PaymentMethod.COD);
		if (isQA) {
			hkPaymentStep.clickPlaceOrder();
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.navigate().to(GlobalVar.baseAppURL + testdata.get("myorderUrl"));
			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);
		}
	}

	@Test(priority = 8, enabled = true, groups = { "qa" })
	public void testComboPackQuickBuyGuestSignUp() {
		System.out.println("##testComboPackQuickBuyGuestSignUp starts..");

		driver.get(GlobalVar.baseAppURL + testdata.get("ComboPack_Url"));
		Assert.assertTrue(hkPdpStep.verifyPDPPageLoaded1(),"pdp page not loaded");

		Assert.assertTrue(hkPdpStep.checkComboPackQuantity(2), "pack quantity mismatch");

		hkPdpStep.QuickBuyOnPdp();
		Assert.assertTrue(guestLoginStep.verifyGuestLoginPageLoaded(), "Fail to load Guest Login page..");

		hkHomeStep.performGuestSignup();

		hkAddressStep.addNewAddressForNewSignUpUser();
		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");
		hkPaymentStep.selectPaymentOption(PaymentMethod.COD);

		if (isQA) {
			hkPaymentStep.clickPlaceOrder();
			Assert.assertTrue(ordrSuccessStep.verifyOrderSuccessPageLoaded(), "Fail to load Order Success page..");

			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.navigate().to(GlobalVar.baseAppURL + testdata.get("myorderUrl"));
			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);
		}
	}

	@Test(priority = 9, enabled = true, groups = { "qa", "beta" })
	public void testOrderPlaceComboProduct() {
		System.out.println("## ORDER PLACE COMBO PRODUCT Test starts..");

		hkHomeStep.performLoginWithMobileOtpAndRemoveCartItem(testdata.get("Login_User_Mobile_6"));
		Assert.assertTrue(hkHomeStep.verifyUserLogin(), "User failed to Login");
		driver.get(GlobalVar.baseAppURL + testdata.get("ComboPack_Url"));
		Assert.assertTrue(hkPdpStep.verifyPDPPageLoaded1(),"pdp page not loaded");

		Assert.assertTrue(hkPdpStep.checkComboPackQuantity(2), "pack quantity mismatch");

		hkPdpStep.addVariantToCart();
		Assert.assertTrue(hkCartStep.verifyCartPageLoaded(),"cart page not loaded");
		hkCartStep.removeHKCash();
		hkCartStep.clickProceedToCheckout();

		hkAddressStep.selectDeliveryAddress();

		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");

		hkPaymentStep.selectPaymentOption(PaymentMethod.COD);
		if (isQA) {
			hkPaymentStep.clickPlaceOrder();
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.navigate().to(GlobalVar.baseAppURL + testdata.get("myorderUrl"));
			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);
		}
	}

	@Test(priority = 10, groups = { "qa" })
	public void orderPlacementOfSearchComboPackGuestSignUp() {
		System.out.println("##search combo pack order placement of guest signup Test starts..");

		hkHomeStep.visitSearchPage(testdata.get("HkComboPack_keyword"));
		categoryStep.selectSortByPriceHL();
		WebDriverUtil.staticWait(3);
		categoryStep.clickFirstPVLinkFromMoreResult();

		Assert.assertTrue(hkPdpStep.checkComboPackQuantity(2), "pack quantity mismatch");

		hkPdpStep.clickOnQuickBuy();

		hkHomeStep.performGuestSignup();
		hkAddressStep.addNewAddressForNewSignUpUser();

		Assert.assertTrue(hkPaymentStep.verifyPaymentPageLoaded(), "Fail to load Payment page..");

		hkPaymentStep.selectPaymentOption(PaymentMethod.COD);
		if (isQA) {

			hkPaymentStep.clickPlaceOrder();
			gatewayOrderId = ordrSuccessStep.getGatewayOrderId();
			Assert.assertTrue(ordrSuccessStep.performValidationForOrder(gatewayOrderId),
					"DB Validations failed for order placed..");
			driver.navigate().to(GlobalVar.baseAppURL + testdata.get("myorderUrl"));
			hkMyAcntStep.performCancelCODOrder(gatewayOrderId);
		}
	}

}
