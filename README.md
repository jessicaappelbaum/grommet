# grommet
A reagent app to test Grommet for theming and general use

### How I set up this project with Grommet:
(instructions were from Tyler, sort of hacky but allows us to experiment with Grommet)

1. `npm install -g bower`
2. `lein new reagent grommet`
3. in the project `bower install grommet`
4. make a file in `src` called `deps.cljs`
5. put this content in the file (without a ns name)

```
{:foreign-libs
  [{:file "bower_components/grommet/grommet.js"
    :provides ["cljsjs.grommet"]}]}
```
    
1. moved and renamed the `grommet.css` file to `resources/public/site.css`
2. to use grommet `(:require [cljsjs.grommet])`
3. to use it how Tyler set us up to use semantic, add this somewhere

```
(def ^:private grommet js/Grommet)
(defn $ [kw]
  (let [c-keys (str/split (name kw) ".")]
    (apply goog.object/getValueByKeys grommet c-keys)))
```
