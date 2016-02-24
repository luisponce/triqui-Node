/**
* Notification.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/

module.exports = {

  	attributes: {
  		type: {
  			type: 'string',
  			enum: ['gameinvite'],
  			defaultsTo: 'gameinvite'
  		},
  		sender: {
  			model: 'Player',
  			required: true
  		}, 
  		to: {
  			model: 'Player',
  			required: true
  		},
      accepted: {
        type: 'boolean',
        defaultsTo: 'false'
      }
  	}
};

