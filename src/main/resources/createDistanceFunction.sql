 CREATE OR REPLACE FUNCTION distance(lat1 FLOAT, lon1 FLOAT, lat2 FLOAT, lon2 FLOAT) RETURNS FLOAT AS '
DECLARE
        dist float = 0;
        radlat1 float;
        radlat2 float;
        theta float;
        radtheta float;
BEGIN
    IF lat1 = lat2 AND lon1 = lon2
                THEN RETURN dist;
            ELSE
                radlat1 = pi() * lat1 / 180;
                radlat2 = pi() * lat2 / 180;
                theta = lon1 - lon2;
                radtheta = pi() * theta / 180;
                dist = sin(radlat1) * sin(radlat2) + cos(radlat1) * cos(radlat2) * cos(radtheta);

                IF dist > 1 THEN dist = 1; END IF;

                dist = acos(dist);
                dist = dist * 180 / pi();
                dist = dist * 60 * 1.1515;


                RETURN dist * 1.609344;
            END IF;
        END
' language plpgsql;