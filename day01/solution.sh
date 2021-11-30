#!/bin/bash

is_prime()
{
    half=$(($1 / 2))
    i=2

    while [ "$i" -le "$half" ]; do
        [ "$(($1 % $i))" = 0 ] && return 0
        i=$((i + 1))
    done
    return 1
}

is_not_prime()
{
    is_prime $1
    [ "$?" = 0 ] && return 1; return 0
}

part_1()
{
    echo "Part 1"
    index=0
    total=0

    while IFS= read -r number || [ -n "$number" ]; do
        is_prime "$number"
        [ "$?" = 1 ] && total=$((total + (number * index)))
        index=$((index + 1))
    done < "input.txt"
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
    done < "input.txt"
    echo "$total"
}

solve()
{
    [ "$part" = "part1" ] && part_1 || part_2
}
 solve
