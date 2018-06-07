
/* Common */
jQuery(function($){
	/* Gnb */
	$('.btn_nav').click(function() {
		$('#header nav').addClass('open');
		$('.dim_header').fadeIn();
	});

	$('.dim_header').click(function() {
		$('#header nav').removeClass('open');
		$('.dim_header').fadeOut();
	});

	/* Datepicker */
	$("#datepicker01").datepicker();

	$('.datepicker01').click(function() {
		$('#datepicker01').focus();
	});

	/* Top button */
	$(document).ready(function() {
		var duration = 500;
		
		$('.btn_top').click(function(event) {
			event.preventDefault();
			jQuery('html, body').animate({scrollTop: 0}, duration);
			return false;
		});
	});

});
	


