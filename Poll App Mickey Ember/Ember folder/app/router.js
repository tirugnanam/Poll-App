import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.route('home');
  this.route('signup');
  this.route('login');
  this.route('profile');
  this.route('poll_create');
  this.route('poll_request');
  this.route('poll_result');
  this.route('poll_response');
});

export default Router;
