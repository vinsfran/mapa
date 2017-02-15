function initMap(data, mapType){
    var asuncion = new google.maps.LatLng(-25.2929451, -57.6072704);

    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -25.2929451, lng: -57.6072704},
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.TERRAIN
    });

    if(mapType === 'heatmap'){
        heatmap(map, data);
    }else if(mapType === 'markers'){
        markers(map, data);
    }
}

function heatmap(map, data) {

    var points = [];

    $.each(data, function( index, d ) {

        points[index] = new google.maps.LatLng(d.lat, d.lng)

    });

    var heatmap = new google.maps.visualization.HeatmapLayer({
        data: points,
        map: map
    });
}

function markers(map, data){

    var bounds = new google.maps.LatLngBounds();
    var infoWindow = new google.maps.InfoWindow(), marker, i;

    $.each(data, function( index, d ) {

        var infoWindowContent =
            '<div class="info_content">' +
                '<h3>' + d.title + '</h3>' +
                '<p>' + d.direccion + '</p>' +
                '<p>' + d.tipoReclamo + '</p>' +
            '</div>'
        ;

        var position = new google.maps.LatLng(d.lat, d.lng);
        bounds.extend(position);

        marker = new google.maps.Marker({
            position: position,
            map: map,
            title: d.title
        });

        // Se agrega una información por cada marker
        google.maps.event.addListener(marker, 'click', (function(marker, i) {
            return function() {
                infoWindow.setContent(infoWindowContent);
                infoWindow.open(map, marker);
            }
        })(marker, i));

    });

}