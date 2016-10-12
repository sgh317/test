jQuery(function($) {
	$(document).ready(function() {
		$('.topbar').stickUp();
	});
});

function goBack(){
	window.history.back();
};
function goHome(){
	window.location.href='../Home/index.html?random='+ Math.random();
};