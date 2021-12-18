#/bin/sh

DAY_TO_COPY="day$1"
CURRENT_DAY="${PWD##*/}"

[ -z "$DAY_TO_COPY" ] && {
    echo "Specify which day to copy from, like so: `copy_day.sh 04`"
    retrn -1
}

TARGET_DIR="$PWD"
SOURCE_DIR="$PWD/../$DAY_TO_COPY"

pushd "$SOURCE_DIR" >/dev/null

for item in src {test_,}input.txt pom.xml; do
    cp -r $item $TARGET_DIR
done
 
popd >/dev/null

find . -type f -exec sed -i -e s/$DAY_TO_COPY/$CURRENT_DAY/g '{}' \;

mv src/main/java/aoc/$DAY_TO_COPY src/main/java/aoc/$CURRENT_DAY
