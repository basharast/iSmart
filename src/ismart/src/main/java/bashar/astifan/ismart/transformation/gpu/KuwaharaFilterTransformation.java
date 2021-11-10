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

import bashar.astifan.ismart.smart.objects.gpuimage.GPUImageKuwaharaFilter;


/**
 * Kuwahara all the colors in the image.
 *
 * The radius to sample from when creating the brush-stroke effect, with a default of 25.
 * The larger the radius, the slower the filter.
 */
public class KuwaharaFilterTransformation extends GPUFilterTransformation {

  private int mRadius;

  public KuwaharaFilterTransformation(Context context) {
    this(context, 25);
  }

  public KuwaharaFilterTransformation(Context context, int radius) {
    super(context, new GPUImageKuwaharaFilter());
    mRadius = radius;
    GPUImageKuwaharaFilter filter = getFilter();
    filter.setRadius(mRadius);
  }

  @Override public String key() {
    return "KuwaharaFilterTransformation(radius=" + mRadius + ")";
  }
}
