$(document).ready(function () {

    displayItems();

    $('#add-dollar-button').click(function (event) {

        var currentMoneyInput = Number($('#money-input').val());
        var updatedMoneyInput = Number(currentMoneyInput + 1).toFixed(2);
        $('#money-input').val(updatedMoneyInput);

    });

    $('#add-quarter-button').click(function (event) {

        var currentMoneyInput = Number($('#money-input').val());
        var updatedMoneyInput = Number(currentMoneyInput + .25).toFixed(2);
        $('#money-input').val(updatedMoneyInput);

    });

    $('#add-dime-button').click(function (event) {

        var currentMoneyInput = Number($('#money-input').val());
        var updatedMoneyInput = Number(currentMoneyInput + .1).toFixed(2);
        $('#money-input').val(updatedMoneyInput);

    });

    $('#add-nickel-button').click(function (event) {

        var currentMoneyInput = Number($('#money-input').val());
        var updatedMoneyInput = Number(currentMoneyInput + .05).toFixed(2);
        $('#money-input').val(updatedMoneyInput);

    });

    $('#make-purchase-button').click(function (event) {

        if ($('#item-input').val().length < 1) {
            $('#messages-input').val('Please make a selection');
        } else {
            var currentMoneyInput = Number($('#money-input').val());
            var itemSelection = $('#item-input').val();
            purchaseItem(currentMoneyInput, itemSelection);
        }



    });

    $('#return-change-button').click(function (event) {

        $('#money-input').val('');
        $('#messages-input').val('');
        $('#item-input').val('');
        $('#change-input').val('');

        displayItems();


    });



});


function displayItems() {

    var firstItemColumn = $('#group1');

    firstItemColumn.empty();

    $.ajax({

        type: 'GET',
        url: 'http://tsg-vending.herokuapp.com/items',
        success: function (itemArray) {
            $.each(itemArray, function (index, item) {

                var itemId = item.id;
                var itemName = item.name;
                var itemPrice = item.price;
                var itemQty = item.quantity;
                var indexFromOne = (index + 1);

                var ButtonToolbarOpenTag = '<div class="btn-toolbar">';
                var ButtonToolbarCloseTag = '</div>';

                if ((indexFromOne + 2) % 3 === 0) {
                    firstItemColumn.append(ButtonToolbarOpenTag);

                }

                var button = '<button type="button" id="button-item-id-' + itemId + '-button" class="btn btn-default" onclick="selectItem(' + itemId + ')">';
                button += '<p style="color:white">-------------------------------------</p>'
                button += '<p>' + indexFromOne + '</p>';
                button += '<input type="hidden" id =' + itemId + '</>';
                button += '<p>' + itemName + '</p>';
                button += '<p>$' + itemPrice + '</p>';
                button += '<p>Quantity left: ' + itemQty + '</p>';
                button += '</button>';


                firstItemColumn.append(button);



                if (indexFromOne % 3 === 0) {
                    firstItemColumn.append(ButtonToolbarCloseTag);
                    firstItemColumn.append('<br>');
                    firstItemColumn.append('<br>');
                }


            });

        }

    });

}

selectItem(itemId);

function selectItem(itemId) {

    $('#item-input').val(itemId);
    $('#messages-input').val('');

}

function purchaseItem(currentMoneyInput, itemSelection) {

    $.ajax({

        type: 'POST',
        url: 'http://tsg-vending.herokuapp.com/money/' + currentMoneyInput + '/item/' + itemSelection,
        success: function (changeObject) {


            $('#messages-input').val('Thank you!!');
            var preceding = false;

            var changeMessage = ''

            if (changeObject.quarters > 0) {

                changeMessage += changeObject.quarters + ' quarters'
                var preceding = true;
            }

            if (changeObject.dimes > 0 && preceding) {

                changeMessage += ", " + changeObject.dimes + ' dimes'
                var preceding = true;
            }

            if (changeObject.dimes > 0 && !preceding) {

                changeMessage += changeObject.dimes + ' dimes'
                var preceding = true;
            }

            if (changeObject.nickels > 0 && preceding) {

                changeMessage += ", " + changeObject.nickels + ' nickels'
                var preceding = true;
            }

            if (changeObject.nickels > 0 && !preceding) {

                changeMessage += changeObject.nickels + ' nickels'
                var preceding = true;
            }

            if (changeObject.pennies > 0 && preceding) {

                changeMessage += ", " + changeObject.pennies + ' pennies'
            }

            if (changeObject.pennies > 0 && !preceding) {

                changeMessage += changeObject.pennies + ' pennies'
            }


            $('#change-input').val(changeMessage);

            displayItems();
            $('#money-input').val('');
            $('#item-input').val('');
        },

        error: function (jqXHR) {

            var messageText = jqXHR.responseText.substring(jqXHR.responseText.search(":\"") + 2, jqXHR.responseText.search("}") - 1);

            $('#messages-input').val(messageText);

            displayItems();

        }
        
    });




}




