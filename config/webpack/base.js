const { webpackConfig, merge } = require('@rails/webpacker')
const customConfig = require('./custom.js');
// const webpack = require('webpack');

// # postcss
// environment.loaders.keys().forEach(loaderName => {
//   let loader = environment.loaders.get(loaderName);
//   loader.use.forEach(loaderConfig => {
//     if (loaderConfig.options && loaderConfig.options.config) {
//       loaderConfig.options.postcssOptions = loaderConfig.options.config;
//       delete loaderConfig.options.config;
//     }
//   });
// });

// https://github.com/rails/webpacker/issues/2155
// environment.loaders.get('sass').use.splice(-1, 0, {
//   loader: 'resolve-url-loader'
// })

// environment.config.externals = {
//   gon: 'gon',
// };

// environment.plugins.prepend('Provide',
//   new webpack.ProvidePlugin({
//     $: 'jquery/src/jquery',
//     jQuery: 'jquery/src/jquery',
//   }),
// );

// console.log(loaders);
const combinedConfig = merge(webpackConfig, customConfig);
console.log(combinedConfig);

module.exports = combinedConfig;
