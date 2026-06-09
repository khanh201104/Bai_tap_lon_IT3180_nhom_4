document.addEventListener("DOMContentLoaded", function () {
    const tables = document.querySelectorAll("table.table");

    tables.forEach(function (table) {
        makeTableSortable(table);
    });
});

function makeTableSortable(table) {
    const thead = table.querySelector("thead");
    const tbody = table.querySelector("tbody");

    if (!thead || !tbody) {
        return;
    }

    const headers = thead.querySelectorAll("th");

    headers.forEach(function (header, index) {
        const headerText = header.innerText.trim().toLowerCase();

        if (
            headerText === "thao tác" ||
            headerText === "hành động" ||
            header.hasAttribute("data-no-sort")
        ) {
            return;
        }

        header.classList.add("sortable-header");
        header.setAttribute("data-sort-direction", "none");

        header.addEventListener("click", function () {
            const currentDirection = header.getAttribute("data-sort-direction");
            const nextDirection = currentDirection === "asc" ? "desc" : "asc";

            headers.forEach(function (h) {
                h.setAttribute("data-sort-direction", "none");
                h.classList.remove("sort-asc", "sort-desc");
            });

            header.setAttribute("data-sort-direction", nextDirection);
            header.classList.add(nextDirection === "asc" ? "sort-asc" : "sort-desc");

            sortTableRows(table, index, nextDirection);
        });
    });
}

function sortTableRows(table, columnIndex, direction) {
    const tbody = table.querySelector("tbody");
    const rows = Array.from(tbody.querySelectorAll("tr"));

    const sortableRows = rows.filter(function (row) {
        const cells = row.querySelectorAll("td");
        return cells.length > 1 && !row.querySelector("td[colspan]");
    });

    const emptyRows = rows.filter(function (row) {
        const cells = row.querySelectorAll("td");
        return cells.length <= 1 || row.querySelector("td[colspan]");
    });

    sortableRows.sort(function (rowA, rowB) {
        const cellA = rowA.querySelectorAll("td")[columnIndex];
        const cellB = rowB.querySelectorAll("td")[columnIndex];

        const valueA = normalizeValue(cellA ? cellA.innerText.trim() : "");
        const valueB = normalizeValue(cellB ? cellB.innerText.trim() : "");

        let result;

        if (valueA.type === "number" && valueB.type === "number") {
            result = valueA.value - valueB.value;
        } else if (valueA.type === "date" && valueB.type === "date") {
            result = valueA.value - valueB.value;
        } else {
            result = valueA.value.localeCompare(valueB.value, "vi", {
                sensitivity: "base",
                numeric: true
            });
        }

        return direction === "asc" ? result : -result;
    });

    tbody.innerHTML = "";

    sortableRows.forEach(function (row) {
        tbody.appendChild(row);
    });

    emptyRows.forEach(function (row) {
        tbody.appendChild(row);
    });
}

function normalizeValue(rawValue) {
    if (!rawValue) {
        return {
            type: "text",
            value: ""
        };
    }

    const value = rawValue.trim();

    const dateMatch = value.match(/^(\d{2})\/(\d{2})\/(\d{4})$/);
    if (dateMatch) {
        const day = parseInt(dateMatch[1], 10);
        const month = parseInt(dateMatch[2], 10) - 1;
        const year = parseInt(dateMatch[3], 10);

        return {
            type: "date",
            value: new Date(year, month, day).getTime()
        };
    }

    const cleanedNumber = value
        .replaceAll(".", "")
        .replaceAll(",", "")
        .replaceAll("đ", "")
        .replaceAll("VNĐ", "")
        .replaceAll("vnđ", "")
        .replace(/\s/g, "");

    if (/^-?\d+(\.\d+)?$/.test(cleanedNumber)) {
        return {
            type: "number",
            value: Number(cleanedNumber)
        };
    }

    return {
        type: "text",
        value: value.toLowerCase()
    };
}