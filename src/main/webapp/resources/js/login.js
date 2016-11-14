function handleLoginRequest(xhr, status, args) {
	if (args.validationFailed || !args.loggedIn) {
		PF('loginDialog').jq.effect("shake", {
			times : 5
		}, 100);
		PF('loginDialog').show();
	} else {
		PF('loginDialog').hide();
		$('#loginLing').fadeOut();
	}
}

function handleLoginBookingRequest(xhr, status, args) {
	if (args.validationFailed || !args.loggedIn) {
		PF('loginBookingDialog').jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF('loginBookingDialog').hide();
		$('#loginLing').fadeOut();
	}
}