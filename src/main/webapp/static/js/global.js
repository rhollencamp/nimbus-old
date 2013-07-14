/*
 * Copyright 2013 rhollencamp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


$("#progressbar").progressbar({
	value: false
});
$("#spinner").hide();
$(document).ajaxStart(function() {
	$("#spinner").dialog({
		dialogClass: "spinner-dialog",
		modal: true,
		closeOnEscape: false,
		resizable: false,
		height: "auto",
		minHeight: 0,
		close: function() {
			$(this).destroy();
		}
	});
});
$(document).ajaxStop(function() {
	$("#spinner").dialog("destroy");
});