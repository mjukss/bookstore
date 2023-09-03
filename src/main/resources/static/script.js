const pageSize = document.querySelector(".page_size_selector");
const sortButtons = document.querySelectorAll('[data-sort]');
const createdAtSortButton = document.querySelector('[data-sort="createdAt"]')

if (size === null) {
    pageSize.value = 50
}

if (sortBy === null) {
    createdAtSortButton.classList.add('button_active')
    addOrderArrow(createdAtSortButton)
}

sortButtons.forEach(button => {
    const name = button.getAttribute('data-sort')
    if (name === sortBy) {
        button.classList.add('button_active')
        addOrderArrow(button)
    }
})

function restore() {
    document.querySelectorAll(".price").forEach(el => {
        el.classList.remove("hidden")
        el.parentElement.querySelector(".table_cell_input")?.classList.add("hidden")
    })
}

document.addEventListener("click", (event) => {
    if (event.target.parentElement.classList.contains("editable")) {
        if (event.target.parentElement.querySelector(".price").classList.contains("hidden")) {
            restore()
        } else {
            restore()
            event.target.parentElement.querySelector(".price").classList.add("hidden")
            event.target.parentElement.querySelector(".table_cell_input").classList.remove("hidden")
            event.target.parentElement.querySelector(".price_input").focus()
        }
    }
})

const sizeParam = () => size ? "&size=" + size : ""
const sortByParam = () => sortBy ? "&sortBy=" + sortBy : ""
const orderParam = () => order ? "&order=" + order : ""

function jumpToPage(p) {
    console.log(p)
    window.location.search = "page=" + p + sizeParam() + sortByParam() + orderParam()
}

function incPageBy(inc) {
    window.location.search = "page=" + (page + inc) + sizeParam() + sortByParam() + orderParam()
}

function swapOrder() {
    if (order === "asc") order = "desc"
    else if (order === "desc") order = "asc"
    else order = "asc"
}

function selectSort(el) {
    const sort = el.getAttribute('data-sort')
    if (sort === sortBy || sortBy === null) {
        swapOrder()
    }
    sortBy = sort
    jumpToPage(0)
}

function setPageSize() {
    size = pageSize.value
    jumpToPage(0)
}

function goHome() {
    console.log("hello")
    window.location = "/"
}

function addOrderArrow(el) {
    if (order === 'asc') el.classList.add('asc')
    else el.classList.add('desc')
}
