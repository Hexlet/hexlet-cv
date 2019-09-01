const { environment } = require('@rails/webpacker')

environment.config.externals = {
  gon: 'gon'
}

module.exports = environment
