package com.example.githubviewerapp.ui.feature

import com.example.githubviewerapp.ui.feature.home.HomeViewModelTest
import com.example.githubviewerapp.ui.feature.details.DetailsViewModelTest
import com.example.githubviewerapp.ui.feature.issue.IssuesViewModelTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    HomeViewModelTest::class,
    DetailsViewModelTest::class,
    IssuesViewModelTest::class
)
class TestSuite