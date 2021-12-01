#!/bin/bash

INPUT="input.txt"
TEST="test_input.txt"
is_current_bigger()
{
    [ "$1" -gt "$2" ] && echo "yes" || echo "no"
}

part_1()
{
    echo "Part 1"
    previous="$(head -n1 $1)"

    while IFS= read -r number || [ -n "$number" ]; do
        [ "$(is_current_bigger $number $previous)" = "yes" ] && {
            total=$((total + 1))
        }
        previous="$number"
    done < "$1"
    echo "$total"
}

part_2()
{
    echo "Part 2"
    index=0
    total=0

    while IFS= read -r number || [ -n "$number" ]; do
        is_not_prime "$number"
        if [ "$?" = 1 ]; then
            [ "$((index % 2))" = 0 ] && total="$((total + number))" || total="$((total - number))"
        fi

        index="$((index + 1))"
    done < "$1"
    echo "$total"
}

solve()
{
    [ "$part" = "part1" ] && part_1 "$1" || part_2 "$1"
}
 solve "$INPUT"
