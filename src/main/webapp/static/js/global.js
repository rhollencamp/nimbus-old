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

// vtype for password confirmation
Ext.apply(Ext.form.field.VTypes, {
	password: function(val, field) {
		if (field.initialPassField) {
			var pwd = field.up('form').down('#' + field.initialPassField);
			return (val === pwd.getValue());
		}
		return true;
	},
	passwordText: 'Passwords do not match'
});

Ext.define('Ext.app.HomePanel', {
	extend: 'Ext.panel.Panel',

	i18n: {
		title: '<h1>Nimbus Password Manager</h1>',
		body: 'The open source cloud password manager. Create an account or log in below',
		logIn: 'Log In',
		register: 'Register'
	},

	registerWindow: null,
	logInWindow: null,

	initComponent: function() {
		Ext.apply(this, {
			title: this.i18n.title,
			bodyPadding: 20,
			items: [{
					margin: '0 0 20 0',
					xtype: 'component',
					html: this.i18n.body
				}, {
					xtype: 'container',
					layout: {
						type: 'hbox',
						pack: 'center'
					},
					items: [{
							xtype: 'button',
							scale: 'large',
							text: this.i18n.register,
							handler: Ext.Function.bind(this.showRegisterForm, this),
							margins: {top: 0, right: 5, bottom: 0, left: 0}
						}, {
							xtype: 'button',
							scale: 'large',
							text: this.i18n.logIn,
							handler: Ext.Function.bind(this.logIn, this)
						}]
				}]
		});
		this.callParent(arguments);
	},

	showRegisterForm: function() {
		if (!this.registerWindow) {
			this.registerWindow = Ext.widget('window', {
				title: this.i18n.register,
				closeAction: 'hide',
				layout: 'fit',
				resizable: false,
				modal: true,
				items: Ext.create('Ext.app.Register')
			});
		}
		this.registerWindow.show();
	},

	logIn: function() {
		if (!this.logInWindow) {
			this.logInWindow = Ext.widget('window', {
				title: this.i18n.logIn,
				closeAction: 'hide',
				layout: 'fit',
				resizable: false,
				modal: true,
				items: Ext.create('Ext.app.LogIn')
			});
		}
		this.logInWindow.show();
	}
});

Ext.define('Ext.app.Register', {
	extend: 'Ext.form.Panel',

	i18n: {
		field: {
			userName: 'User Name',
			password: 'Password',
			confirmPassword: 'Confirm Password'
		},
		button: {
			register: 'Register'
		},
		message: {
			completeForm: 'Please complete the form',
			registerSuccessful: 'Account created',
			unexpectedError: 'An unexpected error occured',
			processing: 'Processing...'
		}
	},

	initComponent: function() {
		Ext.apply(this, {
			closeAction: 'hide',
			border: false,
			bodyPadding: 10,
			url: 'register',
			fieldDefaults: {
				labelWidth: 125,
				msgTarget: 'under',
				allowBlank: false
			},
			items: [{
					fieldLabel: this.i18n.field.userName,
					xtype: 'textfield',
					inputAttrTpl: 'spellcheck="false"',
					name: 'userName',
					minLength: 5,
					maxLength: 255
				}, {
					fieldLabel: this.i18n.field.password,
					xtype: 'textfield',
					inputType: 'password',
					id: 'password',
					name: 'password'
				}, {
					fieldLabel: this.i18n.field.confirmPassword,
					xtype: 'textfield',
					inputType: 'password',
					vtype: 'password',
					initialPassField: 'password'
				}],
			buttons: [{
					text: this.i18n.button.register,
					handler: Ext.Function.bind(this.register, this)
				}]
		});
		this.callParent(arguments);
	},

	register: function() {
		var form = this.getForm();
		if (form.isValid()) {
			this.getForm().submit({
				waitMsg: this.i18n.message.processing,
				success: Ext.Function.bind(this.registerSuccess, this),
				failure: Ext.Function.bind(this.registerFailure, this)
			});
		} else {
			Ext.Msg.show({
				msg: this.i18n.message.completeForm,
				buttons: Ext.Msg.OK,
				icon: Ext.Msg.ERROR
			});
		}
	},

	registerSuccess: function(form, action) {
		form.reset();
		this.up().close();
		Ext.Msg.show({
			msg: this.i18n.message.registerSuccessful,
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.INFO
		});
	},

	registerFailure: function(form, action) {
		var msg = action.result.msg ? action.result.msg : this.i18n.message.unexpectedError;
		Ext.Msg.show({
			msg: msg,
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR
		});
	}
});

Ext.define('Ext.app.LogIn', {
	extend: 'Ext.form.Panel',

	i18n: {
		message: {
			processing: 'Processing...'
		},
		error: 'Error',
		completeForm: 'You must enter all required fields',
		logIn: 'Log In',
		userName: 'User Name',
		password: 'Password',
		errorMsg: 'An unexpected error occured'
	},

	initComponent: function() {
		Ext.apply(this, {
			closeAction: 'hide',
			border: false,
			bodyPadding: 10,
			url: 'authenticate',
			fieldDefaults: {
				msgTarget: 'under',
				allowBlank: false
			},
			items: [{
					fieldLabel: this.i18n.userName,
					xtype: 'textfield',
					inputAttrTpl: 'spellcheck="false"',
					name: 'userName'
				}, {
					fieldLabel: this.i18n.password,
					xtype: 'textfield',
					inputType: 'password',
					name: 'password'
				}],
			buttons: [{
					text: this.i18n.logIn,
					handler: Ext.Function.bind(this.logIn, this)
				}]
		});
		this.callParent(arguments);
	},

	logIn: function() {
		var form = this.getForm();
		if (form.isValid()) {
			this.getForm().submit({
				waitMsg: this.i18n.message.processing,
				success: Ext.Function.bind(this.logInSuccess, this),
				failure: Ext.Function.bind(this.logInFailure, this)
			});
		} else {
			Ext.Msg.alert(this.i18n.error, this.i18n.completeForm);
		}
	},

	logInSuccess: function(form, action) {
		window.location.href = action.result.url;
	},

	logInFailure: function(form, action) {
		var msg = this.i18n.errorMsg;
		if (action.result && action.result.msg) {
			msg = action.result.msg;
		}
		Ext.Msg.show({
			msg: msg,
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR
		});
	}
});

Ext.define('Secret', {
	extend: 'Ext.data.Model',
	idProperty: 'uid',
	fields: [{
			name: 'uid',
			type: 'int'
		}, {
			name: 'title',
			type: 'string'
		}, {
			name: 'url',
			type: 'string'
		}, {
			name: 'userName',
			type: 'string'
		}, {
			name: 'password',
			type: 'string'
		}, {
			name: 'note',
			type: 'string'
		}]
});


Ext.define('Ext.app.EditSecret', {
	extend: 'Ext.form.Panel',

	i18n: {
		field: {
			name: 'Account',
			url: 'URL',
			userName: 'User Name',
			password: 'Password',
			note: 'Note'
		},
		button: {
			save: 'Save',
			showPassword: 'Show Password'
		},
		message: {
			completeForm: 'Please complete the form',
			unexpectedError: 'An unexpected error occured'
		}
	},

	initComponent: function() {
		Ext.apply(this, {
			closeAction: 'hide',
			border: false,
			bodyPadding: 10,
			url: 'passwords/save',
			fieldDefaults: {
				msgTarget: 'under',
				allowBlank: false
			},
			items: [{
					xtype: 'hidden',
					name: 'uid'
				}, {
					fieldLabel: this.i18n.field.name,
					xtype: 'textfield',
					name: 'title',
					minLength: 1,
					maxLength: 255
				}, {
					fieldLabel: this.i18n.field.url,
					xtype: 'textfield',
					name: 'url',
					allowBlank: true,
					maxLength: 255
				}, {
					fieldLabel: this.i18n.field.userName,
					xtype: 'textfield',
					name: 'userName',
					allowBlank: true,
					maxLength: 255
				}, {
					fieldLabel: this.i18n.field.password,
					xtype: 'fieldcontainer',
					items: [{
							xtype: 'textfield',
							name: 'password'
						}, {
							xtype: 'button',
							name: 'showPassword',
							text: this.i18n.button.showPassword,
							hidden: true,
							handler: Ext.Function.bind(this.showPassword, this)
						}]
				}, {
					fieldLabel: this.i18n.field.note,
					xtype: 'textareafield',
					name: 'note',
					allowBlank: true,
					maxLength: 2048
				}],
			buttons: [{
					text: this.i18n.button.save,
					handler: Ext.Function.bind(this.save, this)
				}]
		});
		this.callParent(arguments);
	},

	save: function() {
		var form = this.getForm();
		if (form.isValid()) {
			this.getForm().submit({
				jsonSubmit: true,
				success: Ext.Function.bind(this.saveSuccess, this),
				failure: Ext.Function.bind(this.saveFailure, this)
			});
		} else {
			Ext.Msg.show({
				msg: this.i18n.message.completeForm,
				buttons: Ext.Msg.OK,
				icon: Ext.Msg.ERROR
			});
		}
	},

	saveSuccess: function(form, action) {
		form.reset();
		this.up().close();
		Ext.data.StoreManager.lookup('secretStore').load();
	},

	saveFailure: function(form, action) {
		var msg = this.i18n.message.unexpectedError;
		if (action.result && action.result.msg) {
			msg = action.result.msg;
		}
		Ext.Msg.show({
			msg: msg,
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR
		});
	},

	showPassword: function() {
		this.down('textfield[name=password]').show();
		this.down('button').hide();
	}
});

Ext.define('Ext.app.PasswordPanel', {
	extend: 'Ext.grid.Panel',

	i18n: {
		button: {
			addPassword: 'Add Password',
			logOut: 'Log Out'
		},
		column: {
			name: 'Account',
			url: 'URL',
			userName: 'User Name'
		},
		title: {
			addPassword: 'Add Password',
			passwords: 'Passwords'
		},
		tooltip: {
			copyPassword: 'Copy Password to Clipboard',
			viewSecret: 'View Secret',
			deleteSecret: 'Delete Secret'
		}
	},

	editPasswordWindow: null,

	initComponent: function() {
		Ext.apply(this, {
			frame: true,
			minHeight: 500,
			title: this.i18n.title.passwords,
			dockedItems: [{
					xtype: 'toolbar',
					items: [{
							iconCls: 'icon-add',
							text: this.i18n.button.addPassword,
							handler: Ext.Function.bind(this.addSecret, this)
						}, '->', {
							iconCls: 'icon-log-out',
							text: this.i18n.button.logOut,
							handler: this.logOut
					}]
				}],
			columns: [{
					text: this.i18n.column.name,
					dataIndex: 'title',
					flex: 1
				}, {
					text: this.i18n.column.url,
					dataIndex: 'url',
					renderer: this.formatUrl,
					flex: 1
				}, {
					text: this.i18n.column.userName,
					dataIndex: 'userName',
					flex: 1
				}, {
					xtype: 'actioncolumn',
					width: 56, // 16 pixels per icon, 8 pixels padding
					resizable: false,
					menuDisabled: true,
					hideable: false,
					items: [{
							clipboard: true,
							icon: 'static/icons/document-copy.png',
							tooltip: this.i18n.tooltip.copyPassword
						}, {
							icon: 'static/icons/magnifier.png',
							tooltip: this.i18n.tooltip.viewSecret,
							handler: Ext.Function.bind(this.viewSecret, this)
						}, {
							icon: 'static/icons/cross-circle.png',
							tooltip: this.i18n.tooltip.deleteSecret,
							handler: Ext.Function.bind(this.deleteSecret, this)
					}]
				}],
			store: Ext.create('Ext.data.Store', {
				storeId: 'secretStore',
				model: 'Secret',
				proxy: {
					type: 'ajax',
					api: {
						destroy: 'passwords/delete'
					},
					url: 'passwords/list'
				},
				autoLoad: true,
				autoSync: true
			})
		});
		this.callParent(arguments);

		this.getView().on('refresh', Ext.Function.bind(this.gridViewRefresh, this));
	},

	/**
	 * When the grid view refreshes, find any copy buttons that are on the grid and set them up with ZeroClipboard
	 *
	 * @todo figure out a way to have the Ext QuickTips not messed up by ZeroClipboard
	 * @param {type} gridView
	 */
	gridViewRefresh: function(gridView) {
		Ext.each(Ext.query('.x-action-col-cell'), function(n) {
			var actionCell = Ext.fly(n);
			var copyEl = actionCell.down('img').dom;
			if (!copyEl.hasAttribute('data-clipboard-text')) {
				var row = actionCell.up('tr').dom;
				var record = gridView.getRecord(row);
				var password = record.get('password');
				copyEl.setAttribute('data-clipboard-text', password);
				new ZeroClipboard(copyEl);
			}
		});
	},

	formatUrl: function(value) {
		var ret = value;
		if (Ext.form.field.VTypes.url(value)) {
			ret = '<a href="' + value + '" target="_blank">' + value + '</a>';
		}
		return ret;
	},

	getEditSecretWindow: function() {
		if (!this.editPasswordWindow) {
			this.editPasswordWindow = Ext.widget('window', {
				title: this.i18n.title.addPassword,
				closeAction: 'hide',
				layout: 'fit',
				resizable: false,
				modal: true,
				items: Ext.create('Ext.app.EditSecret')
			});
		}
		return this.editPasswordWindow;
	},

	viewSecret: function(grid, rowIndex, colIndex) {
		var r = grid.store.getAt(rowIndex);
		var w = this.getEditSecretWindow();
		var f = w.child('form');

		f.down('textfield[name=password]').hide();
		f.down('button').show();
		f.getForm().loadRecord(r);

		w.show();
	},

	logOut: function() {
		window.location.href = '/logOut';
	},

	addSecret: function() {
		var w = this.getEditSecretWindow();
		var f = w.child('form');

		f.down('textfield[name=password]').show();
		f.down('button').hide();
		f.getForm().reset();

		w.show();
	},

	deleteSecret: function(grid, rowIndex, colIndex) {
		grid.store.removeAt(rowIndex);
	}
});
