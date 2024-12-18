package com.android.mindteckcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.mindteckcompose.ui.theme.MindTeckComposeTheme

class MainActivity : ComponentActivity() {
    private val fruits = listOf(
        "apple" to "A red fruit",
        "banana" to "A yellow fruit",
        "orange" to "A citrus fruit",
        "blueberry" to "Small round fruit",
        "strawberry" to "Red, juicy fruit",
        "grape" to "A bunch of small, sweet fruit",
        "kiwi" to "A brown, hairy fruit",
        "mango" to "A tropical, sweet fruit",
        "pineapple" to "A tropical fruit with spiky leaves",
        "watermelon" to "A large, juicy fruit"
    )

    private val nature = listOf(
        "mountain" to "A large elevated landform",
        "river" to "A large flowing body of water",
        "forest" to "A dense area filled with trees",
        "desert" to "A dry, barren land",
        "ocean" to "A vast body of salt water",
        "lake" to "A body of water surrounded by land",
        "waterfall" to "A cascade of water falling from a height",
        "volcano" to "A mountain with a crater that can erupt",
        "canyon" to "A deep gorge, typically with a river flowing through it",
        "savanna" to "A grassy plain with few trees"
    )

    private val transportation = listOf(
        "car" to "A vehicle powered by an engine",
        "bicycle" to "A two-wheeled vehicle powered by pedaling",
        "airplane" to "A powered flying vehicle",
        "boat" to "A vessel designed to float on water",
        "train" to "A series of connected railway cars",
        "bus" to "A large motor vehicle for passengers",
        "helicopter" to "A type of aircraft with rotating blades",
        "scooter" to "A small two-wheeled vehicle",
        "submarine" to "A watercraft capable of underwater operation",
        "tram" to "A rail vehicle operating on streets"
    )

    private val animals = listOf(
        "dog" to "A domesticated carnivorous mammal",
        "cat" to "A small domesticated carnivorous mammal",
        "elephant" to "A large herbivorous mammal with a trunk",
        "tiger" to "A large carnivorous feline",
        "bird" to "A warm-blooded egg-laying vertebrate",
        "lion" to "A large carnivorous feline known as the king of the jungle",
        "giraffe" to "A tall herbivorous mammal with a long neck",
        "panda" to "A large bear-like mammal native to China",
        "zebra" to "A wild horse-like animal with black-and-white stripes",
        "koala" to "A small marsupial native to Australia"
    )

    private val instruments = listOf(
        "guitar" to "A stringed musical instrument",
        "piano" to "A large keyboard musical instrument",
        "drums" to "A percussion instrument",
        "violin" to "A stringed instrument played with a bow",
        "flute" to "A wind instrument",
        "trumpet" to "A brass wind instrument",
        "saxophone" to "A wind instrument made of brass",
        "clarinet" to "A woodwind instrument",
        "keyboard" to "A musical instrument with keys",
        "cello" to "A stringed instrument played while seated"
    )

    private val technology = listOf(
        "computer" to "An electronic device for processing data",
        "phone" to "A mobile device for communication",
        "television" to "An electronic device for watching programs",
        "camera" to "A device used to take photographs or videos",
        "radio" to "A device for receiving audio signals",
        "tablet" to "A portable touchscreen device",
        "laptop" to "A portable personal computer",
        "smartwatch" to "A wearable computing device",
        "headphones" to "A device worn over the ears to listen to audio",
        "speaker" to "A device that produces sound"
    )

    private val images = listOf(
        R.drawable.fruit, R.drawable.nature, R.drawable.transport, R.drawable.animals, R.drawable.musical, R.drawable.technology
    )

    private var currentViewPagerPosition by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MindTeckComposeTheme {
                MainScreen()
            }
        }
    }
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
    @Composable
    fun MainScreen() {
        var isBottomSheetVisible by remember { mutableStateOf(false) }
        var searchQuery by remember { mutableStateOf("") }
        val items = remember(currentViewPagerPosition, searchQuery) {
            getFilteredItemsForCurrentPosition().filter { item ->
                item.first.first.contains(searchQuery, ignoreCase = true)
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                // Add the ViewPager at the top as an item
                item {
                    ViewPager(
                        images = images,
                        onPageSelected = { index -> currentViewPagerPosition = index }
                    )
                }

                // Sticky Header for the SearchBar
                stickyHeader {
                    SearchBar(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = 4.dp) // Add space below the search bar
                    )
                }

                // Add items below the sticky header
                items(items) { item ->
                    ListItem(item = item)
                }
            }

            // Floating Action Button for BottomSheet
            FloatingActionButton(
                onClick = { isBottomSheetVisible = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Open Bottom Sheet")
            }

            // Modal Bottom Sheet
            if (isBottomSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = { isBottomSheetVisible = false },
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                ) {
                    BottomSheetContent(items)
                }
            }
        }
    }

    @Composable
    fun SearchBar(
        searchQuery: String,
        onSearchQueryChange: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .background(
                    color = Color(0x1A808080), // Background color with transparency
                    shape = RoundedCornerShape(12.dp) // Rounded corners
                )
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.Black), // Text style and color
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                decorationBox = { innerTextField ->
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search items", // Query hint
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            )
        }
    }


    @Composable
    fun ListItem(item: Pair<Pair<String, String>, Int>) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
                .background(
                    color = Color(0x1A008080), // Use a similar background as in the XML (translucent)
                    shape = RoundedCornerShape(8.dp) // Same corner radius as `list_itembg`
                )
                .padding(8.dp), // Padding inside the background
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Card(
                modifier = Modifier
                    .size(50.dp), // Match size to 50dp
                shape = RoundedCornerShape(8.dp) // Similar to ShapeableImageView's corner size
            ) {
                Image(
                    painter = painterResource(id = item.second),
                    contentDescription = item.first.first,
                    contentScale = ContentScale.Crop // Equivalent to fitXY in XML
                )
            }

            // Texts Column
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp), // Padding start for space between image and text
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.first.first,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold, // Similar to textStyle="bold"
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = item.first.second,
                    fontSize = 12.sp,
                    color = Color.Gray // Optional: Set color for the secondary text
                )
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ViewPager(images: List<Int>, onPageSelected: (Int) -> Unit) {
        val pagerState = rememberPagerState(
            initialPage = 0, // starting from the first page
            initialPageOffsetFraction = 0f, // default offset fraction
            pageCount = { images.size } // the total number of pages
        )

        // Track the selected page using the pager state
        LaunchedEffect(pagerState.currentPage) {
            onPageSelected(pagerState.currentPage)
        }

        Column {
            HorizontalPager(
                state = pagerState
            ) { page ->
                Card(
                    modifier = Modifier
                        .padding(10.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Image(
                        painter = painterResource(id = images[page]),
                        contentDescription = "Page Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }

            // Dot indicators
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                repeat(images.size) { index ->
                    val color = if (index == pagerState.currentPage) Color.Black else Color.Gray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
        }
    }


    @Composable
    fun BottomSheetContent(items: List<Pair<Pair<String, String>, Int>>) {
        val characterCounts = items
            .joinToString("") { it.first.first.filter { char -> char.isLetter() } }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)

        val statsText = buildAnnotatedString {
            val listSizeText = "List Size: ${items.size}\n"
            val topOccurrencesText = "Top 3 Occurrences:\n"

            // Bold "List Size"
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(listSizeText)
            }

            // Bold "Top 3 Occurrences"
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(topOccurrencesText)
            }

            // Add top 3 occurrences
            characterCounts.forEach { entry ->
                append("${entry.key} = ${entry.value}\n")
            }
        }


        Text(modifier = Modifier.padding(horizontal = 16.dp),text = statsText)
    }

    private fun getFilteredItemsForCurrentPosition(): List<Pair<Pair<String, String>, Int>> {
        val filteredItems = when (currentViewPagerPosition) {
            0 -> fruits
            1 -> nature
            2 -> transportation
            3 -> animals
            4 -> instruments
            5 -> technology
            else -> emptyList()
        }
        return filteredItems.map { it to images[currentViewPagerPosition] }
    }
}
