const { environment } = require('@rails/webpacker');
const webpack = require('webpack');

environment.config.externals = {
  gon: 'gon',
};

environment.plugins.prepend('Provide',
  new webpack.ProvidePlugin({
    $: 'jquery',
    jQuery: 'jquery',
    Popper: ['popper.js', 'default']
  }),
);

module.exports = environment;
