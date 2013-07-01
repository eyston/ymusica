module.exports = function(grunt) {

  vendor_js_files = [
    'client/components/jquery/jquery.js',
    'client/components/rxjs/rx.js',
    'client/components/rxjs/rx.time.js',
    'client/components/rxjs/rx.coincidence.js',
    'client/components/angular/angular.js',
    'client/components/angular-resource/angular-resource.js',
    'client/components/bootstrap/docs/assets/js/bootstrap.js',
    'client/components/modernizr/modernizr.js'
  ];

  vendor_css_files = [
    'client/components/bootstrap/docs/assets/css/bootstrap.css'
  ];

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    copy: {
      vendor: {
        files: [{
          expand: true,
          flatten: true,
          src: vendor_js_files,
          dest: 'client/dist/js/vendor'
        },{
          expand: true,
          flatten: true,
          src: vendor_css_files,
          dest: 'client/dist/css/vendor'
        }]
      },
      app: {
        files: [{
          expand: true,
          cwd: 'client',
          src: ['src/app.js', 'src/**/*.js'],
          dest: 'client/dist/js'
        }]
      },
      index: {
        files: { 'client/dist/index.html': 'client/index.html' }
      },
      css: {
        files: { 'client/dist/css/main.css': 'client/css/main.css' }
      }
    },
    uglify: {
      options: {
        sourceMap: 'client/dist/js/ymusica-map.js',
        sourceMappingURL: 'ymusica-map.js',
        // sourceMapRoot: '../',
        sourceMapPrefix: 1
      },
      dev: {
        files: [{
          src: ['client/src/rx/module.js', 'client/src/app.js', 'client/src/**/*.js'],
          dest: 'client/dist/js/ymusica.js'
        }]
      }
    },
    watch: {
      scripts_dev: {
        files: ['client/src/app.js', 'client/src/**/*.js'],
        tasks: ['uglify:dev', 'copy:app']
      },
      index: {
        files: ['client/index.html'],
        tasks: ['copy:index']
      },
      css: {
        files: ['client/css/main.css'],
        tasks: ['copy:css']
      }
    }
  });

  // Load the plugin that provides the "uglify" task.
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-contrib-copy');
  grunt.loadNpmTasks('grunt-contrib-watch');

  // Default task(s).
  grunt.registerTask('build:dev', ['copy:vendor', 'copy:app', 'copy:index', 'copy:css', 'uglify:dev']);
  grunt.registerTask('run:dev', ['build:dev', 'watch'])

};