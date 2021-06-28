function skinMultiAxis() {
    this.cfg.shadow = false;
    this.cfg.seriesColors = ['#03A9F4', '#E91E63', '#4CAF50', '#FFC107', '#03A9F4', '#E91E63', '#4CAF50'];
    this.cfg.grid = {
        background: '#ffffff',
        borderColor: '#ffffff',
        gridLineColor: '#F5F5F5',
        shadow: false
    };
    this.cfg.axesDefaults = {
        borderWidth: 0.1,
        borderColor: 'bdbdbd',
        rendererOptions: {
            textColor: '#666F77'
        }
    };
    this.cfg.seriesDefaults = {
        shadow: false,
        lineWidth: 1,
        renderer: $.jqplot.BarRenderer,
        markerOptions: {
            shadow: false,
            size: 7,
            style: 'circle'
        }
    }
}

function skinMultiAxisControl() {
    this.cfg.shadow = false;
    this.cfg.seriesColors = ['#FF0000', '#FFF000','#0BB200', '#FFF000', '#FF0000', '#000000'];
    this.cfg.grid = {
        background: '#ffffff',
        borderColor: '#ffffff',
        gridLineColor: '#F5F5F5',
        shadow: false
    };
    this.cfg.axesDefaults = {
        borderWidth: 0.1,
        borderColor: 'bdbdbd',
        rendererOptions: {
            textColor: '#666F77'
        }
    };
    this.cfg.seriesDefaults = {
        shadow: false,
        lineWidth: 2,
        renderer: $.jqplot.BarRenderer,
        markerOptions: {
            show: false,
            shadow: false,
            size: 7,
            style: 'circle'
        }
    }

    this.cfg.legend = {
        show: false
    };
}