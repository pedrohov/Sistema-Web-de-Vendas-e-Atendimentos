$(document).ready(function() {
	
	// Add Product attribute:
	/*$("#newForm\\:addAtrib").click(function() {
		let attrib = document.getElementById("newForm:inpAtrib").value;
		
		if(attrib != "") {
			let attributes = document.getElementsByClassName("attributes")[0];
			
			// Look up for an attribute with the same value:
			let exists = false;
			$(".attribute .col-11").each(function(index) {
				if(attrib === $(this).text()) {
					exists = true;
				}
				console.log($(this).text())
			});
			
			// Add the new attribute if it doesn't exist:
			if(!exists)
				attributes.insertAdjacentHTML('beforeend', '<h:panelGroup class="attribute form-row"><div class="col-11">' + attrib + '</div><div class="col-1 delete-attrib"><i class="fa fa-fw fa-trash" aria-hidden="true"></i></div></h:panelGroup>');
		}
	});
	
	// Delete Product attribute:
	$(document).on('click', '.delete-attrib', function () {
		$(this).closest(".attribute").remove();
	});*/
});