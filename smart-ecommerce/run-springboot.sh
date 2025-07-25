#!/bin/bash

cd ../smart-ecommerce || { echo "Directory 'smart-ecommerce' not found."; exit 1; }

echo "Select a Spring Boot module to run:"
modules=()
i=1
for dir in */ ; do
    if [ -f "$dir/pom.xml" ]; then
        modules+=("$dir")
        echo "$i) ${dir%/}"
        ((i++))
    fi
done

read -p "Enter the number of the project to run: " selection
index=$((selection - 1))

if [[ $index -lt 0 || $index -ge ${#modules[@]} ]]; then
    echo "Invalid selection. Exiting."
    exit 1
fi

selectedModule=${modules[$index]}
cd "$selectedModule" || exit

echo "Running Spring Boot module: ${selectedModule%/}"
mvn spring-boot:run
