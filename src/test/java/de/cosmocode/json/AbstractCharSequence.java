/**
 * Copyright 2010 CosmoCode GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.cosmocode.json;

/**
 * An abstract dummy implementation of
 * the {@link CharSequence} interface allowing
 * it to easily create a {@link CharSequence}
 * on the fly without the need to implement every method.
 *
 * @author Willi Schoenborn
 */
public abstract class AbstractCharSequence implements CharSequence {

    @Override
    public char charAt(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int length() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        throw new UnsupportedOperationException();
    }

}
