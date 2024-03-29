/**
* Player.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/

module.exports = {

	attributes: {
		name: {
			type: 'string',
			unique: true,
			required: true
		},
		status: {
			type: 'string',
			enum: ['waiting', 'playing', 'away'],
			defaultsTo: 'waiting'
		},

		notifications: {
			collection: 'Notification',
			via: 'to'
		}
	}
};

