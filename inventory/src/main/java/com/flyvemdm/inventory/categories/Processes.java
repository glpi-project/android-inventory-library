/**
 * FusionInventory
 *
 * Copyright (C) 2010-2017 by the FusionInventory Development Team.
 *
 * http://www.fusioninventory.org/
 * https://github.com/fusioninventory/fusioninventory-android
 *
 * ------------------------------------------------------------------------
 *
 * LICENSE
 *
 * This file is part of FusionInventory project.
 *
 * FusionInventory is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * FusionInventory is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * ------------------------------------------------------------------------------
 * @update    07/06/2017
 * @license   GPLv2 https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * @link      https://github.com/fusioninventory/fusioninventory-android
 * @link      http://www.fusioninventory.org/
 * ------------------------------------------------------------------------------
 */

package com.flyvemdm.inventory.categories;

import android.content.Context;

public class Processes
        extends Category {

    public Processes(Context xCtx, String xType) {
        super(xCtx, xType);
        // TODO review how to get all the processes
/*
        Category c = new Category(mCtx, "PROCESSES");
        ActivityManager  activityManager = (ActivityManager) mFusionApp.getSystemService(Service.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> ps = activityManager.getRunningAppProcesses();
        for(RunningAppProcessInfo process : ps) {
              content.add(new Processes(mFusionApp,process));
          }
          */
    }

    /**
     * 
     */
    private static final long serialVersionUID = 5399654900099889897L;

   

}
