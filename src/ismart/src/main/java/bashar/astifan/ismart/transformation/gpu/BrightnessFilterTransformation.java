package bashar.astifan.ismart.transformation.gpu;

/**
 * Copyright (C) 2015 Wasabeef
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

import android.content.Context;

import bashar.astifan.ismart.smart.objects.gpuimage.GPUImageBrightnessFilter;

/**
 * brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
 */
public class BrightnessFilterTransformation extends GPUFilterTransformation {

  private float mBrightness;

  public BrightnessFilterTransformation(Context context) {
    this(context, 0.0f);
  }

  public BrightnessFilterTransformation(Context context, float brightness) {
    super(context, new GPUImageBrightnessFilter());
    mBrightness = brightness;
    GPUImageBrightnessFilter filter = getFilter();
    filter.setBrightness(mBrightness);
  }

  @Override public String key() {
    return "BrightnessFilterTransformation(brightness=" + mBrightness + ")";
  }
}
