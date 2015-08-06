package com.google.android.gms.location.sample.geofencing;

import android.location.LocationListener;

import com.google.android.gms.maps.model.LatLng;

public class PolygonTest
{
    public static boolean PointIsInRegion(LatLng point, LatLng[] thePath)
    {
        int crossings = 0;

        int count = thePath.length;
        // for each edge
        for (int i=0; i < count; i++)
        {
            LatLng a = thePath [i];
            int j = i + 1;
            if (j >= count)
            {
                j = 0;
            }
            LatLng b = thePath [j];
            if (RayCrossesSegment(point, a, b))
            {
                crossings++;
            }
        }
        // odd number of crossings?
        return (crossings % 2 == 1);
    }

    private static boolean RayCrossesSegment(LatLng point, LatLng a, LatLng b)
    {
        double px = point.longitude;
        double py = point.latitude;
        double ax = a.longitude;
        double ay = a.latitude;
        double bx = b.longitude;
        double by = b.latitude;
        if (ay > by)
        {
            ax = b.longitude;
            ay = b.latitude;
            bx = a.longitude;
            by = a.latitude;
        }
        // alter longitude to cater for 180 degree crossings
        if (px < 0) { px += 360; };
        if (ax < 0) { ax += 360; };
        if (bx < 0) { bx += 360; };

        if (py == ay || py == by) py += 0.00000001;
        if ((py > by || py < ay) || (px > Math.max(ax, bx))) return false;
        if (px < Math.min(ax, bx)) return true;

        double red = (ax != bx) ? ((by - ay) / (bx - ax)) : Double.POSITIVE_INFINITY;
        double blue = (ax != px) ? ((py - ay) / (px - ax)) : Double.POSITIVE_INFINITY;
        return (blue >= red);
    }
}
