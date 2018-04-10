# Chanchan

Chanchan is a 4chan image scraper and search app.

## chanchan-scraper

Just pass the boards you want to download the images from and the output folder where the files should be stored.

### Usage examples

#### To get all content from the specified board(s):

<em>java -jar chanchan-scraper/target/chanchan-scraper-0.0.1.jar -b -boards=wg,ic,p -chanchan.io.output-path=/your/output/path (optional, defaults to ${HOME}/chanchan)</em>

#### If you want to download content from a specific thread, you can do so as in the example below:

<em>java -jar chanchan-scraper/target/chanchan-scraper-0.0.1.jar -t -board=wg -thread=123</em>
