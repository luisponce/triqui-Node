/**
* Game.js
*
* @description :: TODO: You might write a short summary of how this model works and what it represents here.
* @docs        :: http://sailsjs.org/#!documentation/models
*/

module.exports = {

	attributes: {
		player1: {
			collection: 'Player',
			required: true
		},
		player2: {
			collection: 'Player',
			required: true
		}, 
		board: {
			type: 'array'
		},
		playerTurn: {
			type: 'int'
		}
	}
};

