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

import bashar.astifan.ismart.smart.objects.gpuimage.GPUImageSepiaFilter;


/**
 * Applies a simple sepia effect.
 *
 * The intensity with a default of 1.0.
 */
public class SepiaFilterTransformation extends GPUFilterTransformation {

  private float mIntensity;

  public SepiaFilterTransformation(Context context) {
    this(context, 1.0f);
  }

  public SepiaFilterTransformation(Context context, float intensity) {
    super(context, new GPUImageSepiaFilter());
    mIntensity = intensity;
    GPUImageSepiaFilter filter = getFilter();
    filter.setIntensity(mIntensity);
  }

  @Override public String key() {
    return "SepiaFilterTransformation(intensity=" + mIntensity + ")";
  }
}
