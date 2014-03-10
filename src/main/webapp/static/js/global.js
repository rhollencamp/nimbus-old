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
		title: '<h1>Password Manager</h1>',
		body: 'Welcome to the password manager. TODO.',
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
		register: 'Register',
		userName: 'User Name',
		password: 'Password',
		confirmPassword: 'Confirm Password',
		error: 'Error',
		completeForm: 'Please complete the form',
		registerSuccessful: 'Account created',
		errorMsg: 'An unexpected error occured'
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
					fieldLabel: this.i18n.userName,
					xtype: 'textfield',
					name: 'userName',
					minLength: 5,
					maxLength: 255
				}, {
					fieldLabel: this.i18n.password,
					xtype: 'textfield',
					inputType: 'password',
					id: 'password',
					name: 'password'
				}, {
					fieldLabel: this.i18n.confirmPassword,
					xtype: 'textfield',
					inputType: 'password',
					vtype: 'password',
					initialPassField: 'password'
				}],
			buttons: [{
					text: this.i18n.register,
					handler: Ext.Function.bind(this.register, this)
				}]
		});
		this.callParent(arguments);
	},
	register: function() {
		var form = this.getForm();
		if (form.isValid()) {
			this.getForm().submit({
				success: Ext.Function.bind(this.registerSuccess, this),
				failure: Ext.Function.bind(this.registerFailure, this)
			});
		} else {
			Ext.Msg.alert(this.i18n.error, this.i18n.completeForm);
		}
	},
	registerSuccess: function(form, action) {
		form.reset();
		this.up().close();
		Ext.Msg.alert(this.i18n.register, this.i18n.registerSuccessful);
	},
	registerFailure: function(form, action) {
		var msg = action.result.msg ? action.result.msg : this.i18n.errorMsg;
		Ext.Msg.show({
			title: this.i18n.error,
			msg: msg,
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR
		});
	}
});

Ext.define('Ext.app.LogIn', {
	extend: 'Ext.form.Panel',
	i18n: {
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
		var msg = action.result.msg ? action.result.msg : this.i18n.errorMsg;
		Ext.Msg.show({
			title: this.i18n.error,
			msg: msg,
			buttons: Ext.Msg.OK,
			icon: Ext.Msg.ERROR
		});
	}
});

Ext.define('Secret', {
	extend: 'Ext.data.Model',
	fields: [{
			name: 'uid',
			type: 'int'
		}, {
			name: 'title',
			type: 'string'
		}],
	idProperty: 'uid'
});


Ext.define('Ext.app.PasswordPanel', {
	extend: 'Ext.panel.Panel',
	i18n: {
		title: '<h1>Password Manager</h1>',
		body: 'Welcome to the password manager. TODO.'
	},
	initComponent: function() {
		Ext.apply(this, {
			title: this.i18n.title,
			bodyPadding: 20,
			items: [{
					margin: '0 0 20 0',
					xtype: 'gridpanel',
					columns: [{
							text: 'Title',
							dataIndex: 'title'
						}],
					store: Ext.create('Ext.data.Store', {
						model: 'Secret',
						proxy: {
							type: 'ajax',
							url: 'passwords/list'
						},
						autoLoad: true
					})
				}]
		});
		this.callParent(arguments);
	}
});
