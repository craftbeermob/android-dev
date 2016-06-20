/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.craftbeermob.Classes;

import com.google.android.gms.location.Geofence;

/**
 * Constants used in this sample.
 */
public final class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = Geofence.NEVER_EXPIRE;
    public static final float GEOFENCE_RADIUS_IN_METERS = 0060; // 1 mile, 1.6 km
    public static int MAP_ZOOM_LEVEL = 12;
    public static String TransitionEntered="TransitionEntered";
    public static String connectionString = "DefaultEndpointsProtocol=https;AccountName=craftbeermobstorage;AccountKey=fjwYLMgWPklgHyfuRQ6CjewykyQBKXC+L03GqageNBqCNAZD/kLDM97ZBcEdvqTzsPAPVdoud2sHpPqcs/DR5Q==";

    private Constants() {
    }
}
