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

import bashar.astifan.ismart.smart.objects.gpuimage.GPUImageToonFilter;

/**
 * The threshold at which to apply the edges, default of 0.2.
 * The levels of quantization for the posterization of colors within the scene,
 * with a default of 10.0.
 */
public class ToonFilterTransformation extends GPUFilterTransformation {

  private float mThreshold;
  private float mQuantizationLevels;

  public ToonFilterTransformation(Context context) {
    this(context, .2f, 10.0f);
  }

  public ToonFilterTransformation(Context context, float threshold, float quantizationLevels) {
    super(context, new GPUImageToonFilter());
    mThreshold = threshold;
    mQuantizationLevels = quantizationLevels;
    GPUImageToonFilter filter = getFilter();
    filter.setThreshold(mThreshold);
    filter.setQuantizationLevels(mQuantizationLevels);
  }

  @Override public String key() {
    return "ToonFilterTransformation(threshold=" + mThreshold +
        ",quantizationLevels=" + mQuantizationLevels + ")";
  }
}
